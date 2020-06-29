package com.mit.blocks.workspace;

import com.ardublock.ui.ControllerConfiguration.RightPanelListener;
import com.ardublock.ui.ControllerConfiguration.СontrollerСonfiguration;
import com.ardublock.ui.OpenblocksFrame;
import com.mit.blocks.codeblocks.Block;
import com.mit.blocks.codeblocks.ProcedureOutputManager;
import com.mit.blocks.codeblockutil.*;
import com.mit.blocks.controller.WorkspaceController;
import com.mit.blocks.renderable.BlockUtilities;
import com.mit.blocks.renderable.RenderableBlock;
import com.mit.blocks.workspace.typeblocking.FocusTraversalManager;
import com.mit.blocks.workspace.typeblocking.TypeBlockManager;
import org.w3c.dom.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.xpath.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Рабочее пространство - это основная блочная область, где происходит
 * манипулирование блоками и их сборка. Этот класс управляет блоками, миром,
 * видом, рисованием, перетаскиванием, анимацией.
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class Workspace extends JLayeredPane implements ISupportMemento, RBParent, ChangeListener, ExplorerListener {

    private static final long serialVersionUID = 328149080422L;

    /**
     *
     */
    public static JComponent blocksContainer;

    // the environment wrapps all the components of a workspace (Blocks, RenderableBlocks, BlockStubs, BlockGenus)
    private final WorkspaceEnvironment env = new WorkspaceEnvironment();
    private CPopupMenu activeMenu = null;
    private LabelWidget activeWidget = null;

    /**
     *
     * @return
     */
    public WorkspaceEnvironment getEnv() {
        return this.env;
    }

    /**
     * WorkspaceListeners that monitor: block: added, removed, dropped, label
     * changed, connected, disconnected workspace: scrolled, zoom changed
     */
    private HashSet<WorkspaceListener> workspaceListeners = new HashSet<WorkspaceListener>();

    /**
     * WorkspaceWidgets are components within the workspace other than blocks
     * that include bars, buttons, factory drawers, and single instance widgets
     * such as the MiniMap and the TrashCan.
     */
    private TreeSet<WorkspaceWidget> workspaceWidgets = new TreeSet<WorkspaceWidget>(
            // store these in a sorted set according to their "draw depth"
            new Comparator<WorkspaceWidget>() {
        @Override
        public int compare(WorkspaceWidget w1, WorkspaceWidget w2) {
            // by returning the difference in "draw depth", we make this comparitor
            // sort according to ascending "draw depth" (i.e. front to back)
            double depth1 = getDrawDepth(w1.getJComponent());
            double depth2 = getDrawDepth(w2.getJComponent());

            if (depth1 > depth2) {
                return 1;
            } else if (depth1 < depth2) {
                return -1;
            }
            //TODO ria should NEVER return zero unless (w1 == w2) otherwise widget will not be added!
            //ask daniel about this
            if (w1 != w2) {
                return -1;
            } else {
                //System.err.println("returned 0: this widget will not be added to workspace widgets: "+w1+ "comparing with: "+w2);
                return 0;
            }
        }
    });

    /**
     *
     */
    public static boolean everyPageHasDrawer = true;

    /**
     * The Workspace has a BlockCanvas widget on which blocks actually live. The
     * blockCanvas is what takes care of allowing scrolling and drawing pages,
     * so it is controlled by the Workspace, but it is also a regular
     * WorkspaceWidget for the purposes of drag and drop.
     */
    private BlockCanvas blockCanvas = new BlockCanvas(this);

    /**
     * blockCanvasLayer allows for static components to be laid out beside the
     * block canvas. One example of such a component would be a static block
     * factory. In user testing, we found that novice users performed better
     * with a static block factory than one in which they could drag around and
     * toggle the visibility of.
     */
    public JSplitPane workLayer;

    /**
     *
     */
    public JPanel blockCanvasLayer; //Layer with controller

    /**
     *
     */
    public JSplitPane centerPane;
    private boolean isActiveBasket;

    /**
     *
     */
    public JButton zoomPlus;

    /**
     *
     */
    public JButton zoomMinus;

    /**
     *
     */
    public JButton zoomNormal;

    /**
     *
     */
    public JButton undoAct;

    /**
     *
     */
    public JButton redoAct;

    /**
     *
     */
    public JPanel action_buttons;

    /**
     *
     */
    public JPanel level_two;

    /**
     * MiniMap associated with the blockCanvas
     */
    private ErrWindow errWindow;
    private MiniMap miniMap;

    /**
     *
     */
    public FactoryManager factory;
    
    private RightPanelListener rightPanelListener;
    /**
     *
     */
    public СontrollerСonfiguration controller;
    private final FocusTraversalManager focusManager;

    private final TypeBlockManager typeBlockManager;

    /// RENDERING LAYERS ///
    /**
     *
     */
    public final static Integer PAGE_LAYER = new Integer(0);

    /**
     *
     */
    public final static Integer BLOCK_HIGHLIGHT_LAYER = new Integer(1);

    /**
     *
     */
    public final static Integer BLOCK_LAYER = new Integer(2);

    /**
     *
     */
    public final static Integer WIDGET_LAYER = new Integer(3);

    /**
     *
     */
    public final static Integer DRAGGED_BLOCK_HIGHLIGHT_LAYER = new Integer(4);

    /**
     *
     */
    public final static Integer DRAGGED_BLOCK_LAYER = new Integer(5);

    /**
     *
     */
    public Workspace() {
        
        //super();
        
//        setBackground(Color.WHITE);

        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        KeyStroke keyV = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK);
        im.put(keyV, "deleteAllBlocks");

        am.put("deleteAllBlocks", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenblocksFrame.deleteAllBlocks();
            }
        });

        this.controller = new СontrollerСonfiguration();
        controller.setMinimumSize(new Dimension(100, 100));
        
        rightPanelListener = new RightPanelListener(this);
        this.addWorkspaceListener(this.rightPanelListener);
        
        this.factory = new FactoryManager(this);
        this.addWorkspaceListener(this.factory);
        this.blockCanvas.getHorizontalModel().addChangeListener(this);
        for (final Explorer exp : factory.getNavigator().getExplorers()) {
            exp.addListener(this);
        }

        ZoomSlider zoom = new ZoomSlider(this);
        this.miniMap = new MiniMap(this);
        //this.addWidget(this.miniMap, true, true);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                miniMap.repositionMiniMap();
                blockCanvas.reformBlockCanvas();
                blockCanvasLayer.setSize(getSize());
                blockCanvasLayer.validate();
                blockCanvasLayer.updateUI();
                workLayer.setSize(getSize());
                workLayer.validate();
            }
        });

        isActiveBasket = false;

        this.errWindow = new ErrWindow();

        final JLayeredPane blockCanvasWithDepth = new JLayeredPane();
        blockCanvasWithDepth.setPreferredSize(new Dimension(300, 500));
        final JPanel level_one = new JPanel();
        level_one.setLayout(new BoxLayout(level_one, BoxLayout.X_AXIS));
        level_one.setBounds(0, 0, 650, 500);
        level_one.setMinimumSize(new Dimension(0, 0));
        level_one.add(blockCanvas.getJComponent());
        blockCanvasWithDepth.setBackground(Color.black);
        blockCanvasWithDepth.add(level_one, 2);
        blockCanvasWithDepth.add(errWindow, 4);
        blockCanvasWithDepth.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                blockCanvasWithDepth.getComponents()[1].setBounds(0, 0, blockCanvasWithDepth.getWidth() - 1, blockCanvasWithDepth.getHeight() - 1);
                blockCanvasWithDepth.getComponents()[0].setBounds(blockCanvasWithDepth.getWidth() - 60, blockCanvasWithDepth.getHeight() - 241, 60, 241);
                blockCanvasWithDepth.getComponents()[1].revalidate();
                blockCanvasWithDepth.getComponents()[1].repaint();

            }
        });

        //blockCanvas.getJComponent().setMinimumSize(new Dimension(0, 100));
        //blockCanvas.getJComponent().setPreferredSize(new Dimension(0, 600));
        centerPane = new RSplitPane(JSplitPane.VERTICAL_SPLIT, true,
                blockCanvasWithDepth, errWindow);
        centerPane.setOneTouchExpandable(true);
        centerPane.setDividerSize(6);
//        centerPane.setDividerLocation(centerPane.getMaximumDividerLocation() - 100);
        centerPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (centerPane.getDividerLocation() < centerPane.getMaximumDividerLocation() - 50) {
                    centerPane.setDividerLocation(centerPane.getMaximumDividerLocation() - 50);
                }
                blockCanvasLayer.updateUI();
            }
        });

        workLayer = new RSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
                factory.getJComponent(), centerPane);
        workLayer.setOneTouchExpandable(true);
        workLayer.setDividerSize(6);
        workLayer.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (workLayer.getDividerLocation() > 380) {
                    workLayer.setDividerLocation(380);
                }
                blockCanvasLayer.updateUI();
            }
        });

        blockCanvasLayer = new JPanel(new BorderLayout());
        blockCanvasLayer.add(workLayer, BorderLayout.CENTER);
        blockCanvasLayer.add(controller, BorderLayout.EAST);
        factory.getJComponent().setPreferredSize(new Dimension(350, 50));
        controller.setPreferredSize(new Dimension(300, 50));
        add(blockCanvasLayer, BLOCK_LAYER);

        int size = 45;
        final double coef = 0.2f;
        final double max_zoom = 2.5;
        final double min_zoom = 0.5;
        URL iconURL = Workspace.class.getClassLoader().getResource("com/ardublock/block/buttons/zoom+.png");
        ImageIcon button_icon = new ImageIcon(
                new ImageIcon(iconURL).getImage()
                        .getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
        zoomPlus = new JButton(button_icon);
        zoomPlus.setBorder(BorderFactory.createEmptyBorder());
        zoomPlus.setContentAreaFilled(false);
        zoomPlus.setFocusable(false);
        zoomPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoomPlus.repaint();
                double zoom = Page.zoom + coef;
                if (zoom > max_zoom) {
                    zoom = max_zoom;
                }
                setWorkspaceZoom(zoom);
                PageChangeEventManager.notifyListeners();
            }
        });
        zoomPlus.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });
        InputMap imap = this.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        KeyStroke zoomPlusStr = KeyStroke.getKeyStroke(KeyEvent.VK_ADD, InputEvent.CTRL_DOWN_MASK);
        imap.put(zoomPlusStr, "workspacePlus");
        this.getActionMap().put("workspacePlus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double zoom = Page.zoom + coef;
                if (zoom > max_zoom) {
                    zoom = max_zoom;
                }
                setWorkspaceZoom(zoom);
                PageChangeEventManager.notifyListeners();
            }
        });

        iconURL = Workspace.class.getClassLoader().getResource("com/ardublock/block/buttons/zoom-.png");
        button_icon = new ImageIcon(
                new ImageIcon(iconURL).getImage()
                        .getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
        zoomMinus = new JButton(button_icon);
        zoomMinus.setBorder(BorderFactory.createEmptyBorder());
        zoomMinus.setContentAreaFilled(false);
        zoomMinus.setFocusable(false);
        zoomMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoomMinus.repaint();
                double zoom = Page.zoom - coef;
                if (zoom < min_zoom) {
                    zoom = min_zoom;
                }
                setWorkspaceZoom(zoom);
                PageChangeEventManager.notifyListeners();
            }
        });
        imap = this.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        KeyStroke zoomMinusStr = KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, InputEvent.CTRL_DOWN_MASK);
        imap.put(zoomMinusStr, "workspaceMinus");
        this.getActionMap().put("workspaceMinus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double zoom = Page.zoom - coef;
                if (zoom < min_zoom) {
                    zoom = min_zoom;
                }
                setWorkspaceZoom(zoom);
                PageChangeEventManager.notifyListeners();
            }
        });
        zoomMinus.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });

        iconURL = Workspace.class.getClassLoader().getResource("com/ardublock/block/buttons/zoomNormal.png");
        button_icon = new ImageIcon(
                new ImageIcon(iconURL).getImage()
                        .getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
        zoomNormal = new JButton(button_icon);
        zoomNormal.setBorder(BorderFactory.createEmptyBorder());
        zoomNormal.setContentAreaFilled(false);
        zoomNormal.setFocusable(false);
        zoomNormal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setWorkspaceZoom(1);
                PageChangeEventManager.notifyListeners();
            }
        });
        zoomNormal.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });

        imap = this.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        KeyStroke zoomNormalStr = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK);
        imap.put(zoomNormalStr, "workspaceNormal");
        this.getActionMap().put("workspaceNormal", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setWorkspaceZoom(1);
                PageChangeEventManager.notifyListeners();
            }
        });

        iconURL = Workspace.class.getClassLoader().getResource("com/ardublock/block/buttons/undoButton.png");
        button_icon = new ImageIcon(
                new ImageIcon(iconURL).getImage()
                        .getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
        undoAct = new JButton(button_icon);
        undoAct.setBorder(BorderFactory.createEmptyBorder());
        undoAct.setContentAreaFilled(false);
        undoAct.setFocusable(false);
        undoAct.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Workspace.this.getPageNamed("Main").setUndoScreen();
            }
        });
        undoAct.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });

        iconURL = Workspace.class.getClassLoader().getResource("com/ardublock/block/buttons/redoButton.png");
        button_icon = new ImageIcon(
                new ImageIcon(iconURL).getImage()
                        .getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
        redoAct = new JButton(button_icon);
        redoAct.setBorder(BorderFactory.createEmptyBorder());
        redoAct.setContentAreaFilled(false);
        redoAct.setFocusable(false);
        redoAct.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Workspace.this.getPageNamed("Main").setRedoScreen();
            }
        });
        redoAct.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });

        level_two = new JPanel();
        level_two.setLayout(new BorderLayout());
        level_two.setBackground(new Color(0, 0, 0, 0));
        action_buttons = new JPanel();
        action_buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 1));
        //action_buttons.setLayout(new BoxLayout(action_buttons, BoxLayout.Y_AXIS));

//        action_buttons.add(undoAct);
//        action_buttons.add(redoAct);
        action_buttons.add(zoomPlus);
        action_buttons.add(zoomMinus);
        action_buttons.add(zoomNormal);

        int zoom_panel_x = 500;
        int zoom_panel_y = 500;
        level_two.setBounds(1, 1,
                299, 649);

        action_buttons.setBackground(new Color(255, 255, 255, 0));

        level_two.add(action_buttons, BorderLayout.CENTER);

        blockCanvasWithDepth.add(level_two, new Integer(3));

        validate();
        addPageAt(Page.getBlankPage(this), 0, true); //false

        this.workspaceWidgets.add(factory);

        this.focusManager = new FocusTraversalManager(this);

        this.typeBlockManager = new TypeBlockManager(this, blockCanvas);

    }

    /*
     * Implements explorerEventOccurred method in ExplorerListener interface
     * If event is of type "1" then hides the Minimize page button
     * If event is of type "2" then shows the Minimize page button
     * Event type details can be found in the GlassExplorerEvent class
     */
    /**
     *
     * @param status
     */
    public void setBasket(boolean status) {
        if (isActiveBasket != status) {
            this.factory.setBasket(status);
            isActiveBasket = status;
        }
    }

    /**
     *
     */
    public void fullRandomBlocksRepaint() {
        Random rnd = new Random();
        for (RenderableBlock bl : getFactoryManager().getBlocks()) {
            int r = rnd.nextInt(255);
            int g = rnd.nextInt(255);
            int b = rnd.nextInt(255);
            bl.randomColor = new Color(r, g, b);
            bl.updateBuffImg();
            bl.repaint();
        }
        for (RenderableBlock bl : getPageNamed("Main").getBlocks()) {
            int r = rnd.nextInt(255);
            int g = rnd.nextInt(255);
            int b = rnd.nextInt(255);
            bl.randomColor = new Color(r, g, b);
            bl.updateBuffImg();
            bl.repaint();
        }

    }

    /**
     *
     * @param event
     */
    @Override
    public void explorerEventOccurred(ExplorerEvent event) {
        final Explorer exp = event.getSource();
        if (event.getEventType() == 1) {
            for (final Page p : blockCanvas.getLeftmostPages(exp.getSelectedCanvasWidth())) {
                p.disableMinimize();
            }
        } else if (event.getEventType() == 2) {
            for (final Page p : blockCanvas.getLeftmostPages(exp.getSelectedCanvasWidth())) {
                p.enableMinimize();
            }
        }
    }

    /**
     *
     * @return
     */
    public Dimension getFactorySize() {
        return factory.getNavigator().getJComponent().getSize();
    }

    /**
     *
     * @return
     */
    public Dimension getCanvasSize() {
        return blockCanvas.getCanvas().getSize();
    }

    /**
     *
     * @return
     */
    public Dimension getCanvasOffset() {
        return new Dimension(blockCanvas.getHorizontalModel().getValue() - blockCanvas.getJComponent().getX(),
                blockCanvas.getVerticalModel().getValue() - blockCanvas.getJComponent().getY());
    }

    /**
     *
     * @param pageName
     * @return
     */
    public Page getPageNamed(String pageName) {
        return blockCanvas.getPageNamed(pageName);
    }

    /**
     *
     * @return
     */
    public BlockCanvas getBlockCanvas() {
        return blockCanvas;
    }

    /**
     * @return MiniMap associated with this.blockcanvas
     */
    public MiniMap getMiniMap() {
        return this.miniMap;
    }

    /**
     *
     * @return
     */
    public ErrWindow getErrWindow() {
        return this.errWindow;
    }

    /**
     * Returns the FocusTraversalManager instance
     *
     * @return FocusTraversalManager instance
     */
    public FocusTraversalManager getFocusManager() {
        return focusManager;
    }

    /**
     * Disables the MiniMap from canvas
     */
    public void disableMiniMap() {
        miniMap.hideMiniMap();
    }

    ////////////////
    // WIDGETS
    ////////////////
    private Point p = new Point(0, 0); // this is for speed - faster not to re-create Points

    /**
     * Returns the WorkspaceWidget currently at the specified point
     *
     * @param point the <code>Point2D</code> to get the widget at, given in
     * Workspace (i.e. window) coordinates
     * @return the WorkspaceWidget currently at the specified point
     */
    public WorkspaceWidget getWidgetAt(Point point) {
        Iterator<WorkspaceWidget> it = workspaceWidgets.iterator();
        //TODO: HUGE HACK, get rid of this. bascally, the facotry has priority
        if (factory.contains(
                SwingUtilities.convertPoint((JComponent) this, point, factory.getJComponent()).x,
                SwingUtilities.convertPoint((JComponent) this, point, factory.getJComponent()).y)) {
            return factory;
        }
        WorkspaceWidget widget = null;
        while (it.hasNext()) {
            //convert point to the widgets' coordinate system
            widget = it.next();
            p = SwingUtilities.convertPoint((JComponent) this, point, widget.getJComponent());
            //test if widget contains point and widget is visible
            if (widget.contains(p.x, p.y) && widget.getJComponent().isVisible()) {
                return widget; // because these are sorted by draw depth, the first hit is on top
            }
        }

        return null; // hopefully we never get here
    }

    /**
     * This helper method retuns a fractional "depth" representing the overall
     * z-order of a component in the workspace. For example, a component with a
     * "drawDepth" of 1.9 is most likely the first child (rendered on top of,
     * remember) a component with z-order 2 in this container. 1.99 means the
     * first child of the first child, and so on.
     *
     * @param c - the Component whose draw depth is required. MUST be an
     * eventual child of the Workspace.
     * @return the fractional "drawDepth" of the Component c.
     */
    private double getDrawDepth(Component c) {
        int treeLevel = 0;
        double depth = 0;
        Container p = c.getParent();
        //System.out.println("getting drawer depth for: "+c);
        if (p != null) { //ria added this condition (when removing widgets while resetting, they must have a parent, but by then they have none.
            // figure out how far down the tree this component is
            while (p != this && p != null) {
                p = p.getParent();
                treeLevel++;
            }
            //System.out.println("tree level for "+c+": "+treeLevel);
            // now walk up the tree, assigning small fractions for distant children,
            // and getting more important closer to the top level.
            p = c.getParent();
            for (int level = treeLevel; level >= 0; level--) {
                if (p == null) {
                    break;
                }
                if (level > 0) {
                    depth -= p.getComponentZOrder(c) / Math.pow(10, level);
                } else {
                    depth += p.getComponentZOrder(c);
                }
                c = p;
                p = p.getParent();
            }

            //System.out.println("returned depth "+depth);
            return depth;
        }
        return Double.MAX_VALUE;
    }

    /**
     * Adds the specified widget to this Workspace
     *
     * @param widget the desired widget to add
     * @param floatOverCanvas if true, the Workspace will add and render this
     * widget such that it "floats" above the canvas and its set of blocks. If
     * false, the widget will be laid out beside the canvas. This feature only
     * applies if the specified widget is added graphically to the workspace
     * (addGraphically = true)
     * @param addGraphically a Swing dependent parameter to tell the Workspace
     * whether or not to add the specified widget as a child component. This
     * parameter should be false for widgets that have a parent already
     * specified
     */
    public void addWidget(WorkspaceWidget widget, boolean addGraphically, boolean floatOverCanvas) {
        if (addGraphically) {
            if (floatOverCanvas) {
                this.add((JComponent) widget, WIDGET_LAYER);
                widget.getJComponent().setVisible(true);
                revalidate();
                repaint();
            } else {
                blockCanvas.getJComponent().setPreferredSize(new Dimension(
                        blockCanvas.getWidth() - widget.getJComponent().getWidth(),
                        blockCanvasLayer.getHeight()));
            }
        }
        boolean success = workspaceWidgets.add(widget);
        if (!success) {
            System.err.println("not able to add: " + widget);
        }
    }

    /**
     * Removes the specified widget from this Workspace
     *
     * @param widget the desired widget to remove
     */
    public void removeWidget(WorkspaceWidget widget) {
        workspaceWidgets.remove(widget);
        this.remove((JComponent) widget);
    }

    /**
     * Returns an unmodifiable Iterable over all the WorkspaceWidgets
     *
     * @return an unmodifiable Iterable over all the WorkspaceWidgets
     */
    public Iterable<WorkspaceWidget> getWorkspaceWidgets() {
        return Collections.unmodifiableSet(workspaceWidgets);
    }

    /**
     * Returns the set of all RenderableBlocks in the Workspace. Includes all
     * live blocks on all pages. Does NOT include: (1) Factory blocks, (2) dead
     * blocks, (3) or subset blocks. If no blocks are found, it returns an empty
     * set.
     *
     * @return all the RenderableBlocks in the Workspace or an empty set if none
     * exists.
     */
    public Iterable<RenderableBlock> getRenderableBlocks() {
        //TODO: performance issue, must iterate through all blocks
        return blockCanvas.getBlocks();
    }

    /**
     * Returns the set of all Blocks in the Workspace. Includes all live blocks
     * on all pages. Does NOT include: (1) Factory blocks, (2) dead blocks, (3)
     * or subset blocks. If no blocks are found, it returns an empty set.
     *
     * @return all the Blocks in the Workspace or an empty set if none exists.
     */
    public Iterable<Block> getBlocks() {
        //TODO: performance issue, must iterate through all blocks
        final ArrayList<Block> blocks = new ArrayList<Block>();
        for (final RenderableBlock renderable : blockCanvas.getBlocks()) {
            blocks.add(getEnv().getBlock(renderable.getBlockID()));
        }
        return blocks;
    }

    /**
     * Returns all the RenderableBlocks of the specified genus. Include all live
     * blocks on all pages. Does NOT include: (1) all blocks of a different
     * genus (2) Factory blocks, (3) dead blocks, (4) or subset blocks. If no
     * blocks are found, it returns an empty set.
     *
     * @param genusName - the genus name of the blocks to return
     * @return all the RenderableBlocks of the specified genus or an empty set
     * if none exists.
     */
    public Iterable<RenderableBlock> getRenderableBlocksFromGenus(String genusName) {
        //TODO: performance issue, must iterate through all blocks
        ArrayList<RenderableBlock> blocks = new ArrayList<RenderableBlock>();
        for (RenderableBlock block : blockCanvas.getBlocks()) {
            if (getEnv().getBlock(block.getBlockID()).getGenusName().equals(genusName)) {
                blocks.add(block);
            }
        }
        return blocks;
    }

    /**
     * Returns all the Blocks of the specified genus. Include all live blocks on
     * all pages. Does NOT include: (1) all blocks of a different genus (2)
     * Factory blocks, (3) dead blocks, (4) or subset blocks. If no blocks are
     * found, it returns an empty set.
     *
     * @param genusName - the genus name of the blocks to return
     * @return all the Blocks of the specified genus or an empty set if none
     * exists.
     */
    public Iterable<Block> getBlocksFromGenus(String genusName) {
        //TODO: performance issue, must iterate through all blocks
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (RenderableBlock renderable : blockCanvas.getBlocks()) {
            Block block = getEnv().getBlock(renderable.getBlockID());
            if (block.getGenusName().equals(genusName)) {
                blocks.add(block);
            }
        }
        return blocks;
    }

    /**
     * Returns the top level blocks in the Workspace (blocks that are parents of
     * stacks)
     *
     * @return the top level blocks in the Workspace
     */
    public Iterable<RenderableBlock> getTopLevelBlocks() {
        return blockCanvas.getTopLevelBlocks();
    }

    /**
     * Cleans up all the blocks within the block canvas using the default
     * arrangement algorithm. TODO ria for now its the naive arranger that uses
     * just the y-coor.
     */
    public void cleanUpAllBlocks() {
        blockCanvas.arrangeAllBlocks();
    }

    /**
     * calls TypeBlockManager to copy the highlighted blocks on the canvas
     */
    public void copyBlocks() {
        typeBlockManager.copyBlock(this);
    }

    /**
     * calls TypeBlockManager to pastes the highlighted blocks on the canvas
     */
    public void pasteBlocks() {
        typeBlockManager.pasteBlock(this);
    }

    //////////////////////////
    // WORKSPACE LISTENERS
    //////////////////////////
    /**
     * Adds the specified WorkspaceListener
     *
     * @param listener
     */
    public void addWorkspaceListener(WorkspaceListener listener) {
        if (listener != null) {
            // warn of duplicate adds
            assert (!workspaceListeners.contains(listener)) : "WorkspaceListener " + listener.toString() + " has already been added.";
            workspaceListeners.add(listener);
        }
    }

    /**
     * Removes the specified WorkspaceListener
     *
     * @param listener
     */
    public void removeWorkspaceListener(WorkspaceListener listener) {
        if (listener != null) {
            workspaceListeners.remove(listener);
        }
    }

    /**
     * Notifies all Workspace listeners of the workspace event
     *
     * @param event
     */
    public void notifyListeners(WorkspaceEvent event) {
        for (WorkspaceListener wl : workspaceListeners) {
            wl.workspaceEventOccurred(event);
        }
    }

    ////////////////////
    //TypeBlockManaging
    ////////////////////
    /**
     * Enables TypeBLocking if and only if enabled == true
     *
     * @param enabled
     */
    public void enableTypeBlocking(boolean enabled) {
        typeBlockManager.setEnabled(enabled);
    }

    /**
     * The type block manager, if defined.
     *
     * @return The manager.
     * @see #enableTypeBlocking(boolean)
     */
    public TypeBlockManager getTypeBlockManager() {
        return typeBlockManager;
    }

    ///////////////////
    // WORKSPACE ZOOM
    ///////////////////
    private double zoom = 1.0;

    /**
     * Sets the Workspace zoom at the specified zoom level
     *
     * @param newZoom the desired zoom level
     */
    public void setWorkspaceZoom(double newZoom) {
        double oldZoom = this.zoom;
        int cDX = 0, cDY = 0;

        this.zoom = newZoom;

        BlockUtilities.setZoomLevel(newZoom);
        for (RenderableBlock block : getRenderableBlocks()) {
            block.setZoomLevel(newZoom);
        }
        for (RenderableBlock block : getFactoryManager().getBlocks()) {
            block.setZoomLevel(newZoom);
        }
        for (Page p : getBlockCanvas().getPages()) {
            for (RenderableBlock block : p.getTopLevelBlocks()) {

//                 checks if the x and y position has not been set yet, this happens when
//                 a previously saved project is just opened and the blocks have not been
//                 moved yet. otherwise, the unzoomed X and Y are calculated in RenderableBlock
                if (block.getUnzoomedX() == 0.0 && block.getUnzoomedY() == 0.0) {
                    if (newZoom == 1.0) {
                        block.setUnzoomedX(block.getX());
                        block.setUnzoomedY(block.getY());
                    } else {
                        block.setUnzoomedX(block.calculateUnzoomedX(block.getX()));
                        block.setUnzoomedY(block.calculateUnzoomedY(block.getY()));
                    }
                } else {
                }

                // calculates the new position based on the initial position when zoom is at 1.0
                double coef = oldZoom / newZoom;
                block.setLocation((int) ((double) block.getX() / coef), (int) ((double) block.getY() / coef));
//

                block.redrawFromTop();
                block.repaint();

            }
        }
        Page.setZoomLevel(newZoom);
    }

    /**
     * Returns the current workspace zoom
     *
     * @return the current workspace zoom
     */
    public double getCurrentWorkspaceZoom() {
        return zoom;
    }

    /**
     * Resets the workspace zoom to the default level
     */
    public void setWorkspaceZoomToDefault() {
        this.setWorkspaceZoom(1.0);
    }

    /**
     *
     * @param c
     */
    public void scrollToComponent(JComponent c) {
        blockCanvas.scrollToComponent(c);
    }

    public void stateChanged(ChangeEvent e) {
        List<Explorer> explorers = factory.getNavigator().getExplorers();
        for (Explorer exp : explorers) {
            List<Page> leftMostPages = blockCanvas.getLeftmostPages(exp.getSelectedCanvasWidth());
            boolean expanded = exp.anyCanvasSelected();
            for (Page p : blockCanvas.getPages()) {
                if (expanded) {
                    p.setHide(false);
                }
            }
            for (Page p : leftMostPages) {
                if (expanded) {
                    p.setHide(true);
                }
            }
        }
    }

    ////////////////
    //PAGE METHODS (note: these may change)
    ////////////////
    /**
     * Adds the specified page to the Workspace at the right end of the canvas
     *
     * @param page the desired page to add
     */
    public void addPage(Page page) {
        addPage(page, blockCanvas.numOfPages());
    }

    /**
     * Adds the specified page to the Workspace at the specified position on the
     * canvas
     *
     * @param page the desired page to add
     * @param position
     */
    public void addPage(Page page, int position) {
        //this method assumes that this addPage was a user or file loading
        //event in which case a page added event should be thrown
        addPageAt(page, position, true);
    }

    /**
     * Places the specified page at the specified index. If a page already
     * exists at that index, this method will replace it.
     *
     * @param page the Page to place
     * @param position - the position to place the specified page, where 0 is
     * the leftmost page
     */
    public void putPage(Page page, int position) {
        if (blockCanvas.hasPageAt(position)) {
            removePageAt(position);
        }
        addPageAt(page, blockCanvas.numOfPages(), true);
    }

    /**
     * Adds a Page in the specified position, where position 0 is the leftmost
     * page
     *
     * @param page - the desired Page to add
     * @param index - the desired position of the page
     * @param fireWorkspaceEvent if set to true, will fire a WorkspaceEvent that
     * a Page was added
     */
    private void addPageAt(Page page, int index, boolean fireWorkspaceEvent) {
        blockCanvas.addPage(page, index);
        workspaceWidgets.add(page);
        if (fireWorkspaceEvent) {
            notifyListeners(new WorkspaceEvent(this, page, WorkspaceEvent.PAGE_ADDED));
        }
    }

    /**
     * Removes the specified page from the Workspace at the specified position,
     * where position 0 is the left most page
     *
     * @param position
     */
    public void removePageAt(int position) {
        removePage(blockCanvas.getPageAt(position));
    }

    /**
     * Removes the specified page from the Workspace
     *
     * @param page the desired page to remove
     */
    public void removePage(Page page) {
        boolean success = workspaceWidgets.remove(page);
        if (!success) {
            System.out.println("Page: " + page + ", was NOT removed successfully");
        }
        notifyListeners(new WorkspaceEvent(this, page, WorkspaceEvent.PAGE_REMOVED));
        blockCanvas.removePage(page);
    }

    /**
     * Renames the page with the specified oldName to the specified newName.
     *
     * @param oldName the oldName of the page to rename
     * @param newName the String name to change the page name to
     */
    public void renamePage(String oldName, String newName) {
        Page renamedPage = blockCanvas.renamePage(oldName, newName);
        //TODO ria HACK TO GET DRAWERS AND PAGE IN SYNC
        //as a rule, all relevant data like pages and drawers should be updated before
        //an event is released because the listeners make assumptions on the state
        //of the data.  in the future, have the page rename its drawer
        factory.renameDynamicDrawer(oldName, newName);
        notifyListeners(new WorkspaceEvent(this, renamedPage, oldName, WorkspaceEvent.PAGE_RENAMED));
    }

    /**
     * Returns the number of pages contained within this. By default will always
     * have a page even if a page was not specified. The page will just be
     * blank.
     *
     * @return the number of pages contained within this
     */
    public int getNumPages() {
        return blockCanvas.numOfPages();
    }

    /**
     * Find the page that lies underneath this block CAN RETURN NULL
     *
     * @param block
     * @return
     */
    public Page getCurrentPage(RenderableBlock block) {
        for (Page page : getBlockCanvas().getPages()) {
            if (page.contains(SwingUtilities.convertPoint(block.getParent(), block.getLocation(), page.getJComponent()))) {
                return page;
            }
        }
        return null;
    }

    /**
     * Marks the page of the specified name as being selected. The workspace
     * view may shift to that page.
     *
     * @param page the Page selected
     * @param byUser true if Page was selected by the User
     */
    public void pageSelected(Page page, boolean byUser) {
        blockCanvas.switchViewToPage(page);
    }

    /**
     *
     * @return
     */
    public FactoryManager getFactoryManager() {
        return factory;
    }

    /**
     * Returns an unmodifiable Iterable of all the SearchableContainers within
     * this workspace.
     *
     * @return
     */
    public Iterable<SearchableContainer> getAllSearchableContainers() {
        ArrayList<SearchableContainer> containers = new ArrayList<SearchableContainer>(factory.getSearchableContainers());

        for (WorkspaceWidget w : workspaceWidgets) {
            if (w instanceof SearchableContainer) {
                containers.add((SearchableContainer) w);
            }
        }

        return Collections.unmodifiableList(containers);
    }

    ////////////////////////
    //Subsets             //
    ////////////////////////
    /**
     * Sets up the subsets by clearing all subsets and installing the new
     * collection of subsets. If "usingSys" is true, the the factory and
     * myblocks drawers will be accessible. If "usingSubs" is true, then the
     * subset drawers will be accessible.
     *
     * @param subsets - collection of subsets
     * @param usingSys - true for factory and myblocks
     * @param usingSubs - true for subsets
     */
    public void setupSubsets(Collection<Subset> subsets, boolean usingSys, boolean usingSubs) {
        this.factory.setupSubsets(subsets, usingSys, usingSubs);
    }

    ////////////////////////
    // SAVING AND LOADING //
    ////////////////////////
    /**
     * Returns the node of this.Currently returns the BlockCanvas node only.
     *
     * @param document
     * @return the node of this.
     */
    public Node getSaveNode(Document document) {
        return blockCanvas.getSaveNode(document);
    }

    /**
     * Set the MaxBlockId for WorkspaceEnvironment
     *
     * @param newRoot
     * @param originalLangRoot
     */
    private void setMaxBlockId(Element newRoot, Element originalLangRoot) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr;
        long maxId = 1;
        try {
            expr = xpath.compile("//@id");

            if (newRoot != null) {
                NodeList bgs = (NodeList) expr.evaluate(newRoot, XPathConstants.NODESET);
                for (int i = 0; i < bgs.getLength(); i++) {
                    Attr attr = (Attr) bgs.item(i);
                    long myId = Long.parseLong(attr.getValue());
                    maxId = myId > maxId ? myId : maxId;
                }
            }

            if (originalLangRoot != null) {
                NodeList bgs = (NodeList) expr.evaluate(originalLangRoot, XPathConstants.NODESET);
                for (int i = 0; i < bgs.getLength(); i++) {
                    Attr attr = (Attr) bgs.item(i);
                    long myId = Long.parseLong(attr.getValue());
                    maxId = myId > maxId ? myId : maxId;
                }
            }

            env.setNextBlockID(maxId + 1);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the workspace with the following content: - RenderableBlocks and
     * their associated Block instances that reside within the BlockCanvas
     *
     * @param newRoot the XML Element containing the new desired content. Some
     * of the content in newRoot may override the content in originalLangRoot.
     * (For now, pages are automatically overwritten. In the future, will allow
     * drawers to be optionally overriden or new drawers to be inserted.)
     * @param originalLangRoot the original language/workspace specification
     * content
     * @requires originalLangRoot != null
     */
    public void loadWorkspaceFrom(Element newRoot, Element originalLangRoot) {
        setMaxBlockId(newRoot, originalLangRoot);

        //reset procedure output information POM finishload
        ProcedureOutputManager.finishLoad();

        if (newRoot != null) {
            //PageDrawerLoadingUtils.loadBlockDrawerSets(this, originalLangRoot, factory, controller); //
            //PageDrawerLoadingUtils.loadComponentsSets(this, originalLangRoot, controller);
            //load pages, page drawers, and their blocks from save file
            blockCanvas.loadSaveString(newRoot);
            //load the block drawers specified in the file (may contain
            //custom drawers) and/or the lang def file if the contents specify
//            PageDrawerLoadingUtils.loadBlockDrawerSets(this, originalLangRoot, factory);
            PageDrawerLoadingUtils.loadBlockDrawerSets(this, newRoot, factory, controller);
            //PageDrawerLoadingUtils.loadComponentsSets(this, newRoot, controller);
            loadWorkspaceSettings(newRoot);
        } else {
            //load from original language/workspace root specification
            blockCanvas.loadSaveString(originalLangRoot);
            //load block drawers and their content
            PageDrawerLoadingUtils.loadBlockDrawerSets(this, originalLangRoot, factory, controller);
            PageDrawerLoadingUtils.loadComponentsSets(this, originalLangRoot, controller);
                                   
//            controller.setModuleOnPin("d8", "button");
            loadWorkspaceSettings(originalLangRoot);
        }

    }

    /**
     * Loads the settings for this Workspace. Settings include specification of
     * programming environment features such as the search bar, minimap, or
     * zooming.
     *
     * @param root
     */
    private void loadWorkspaceSettings(Element root) {
        Pattern attrExtractor = Pattern.compile("\"(.*)\"");
        Matcher nameMatcher;

        NodeList miniMapNodes = root.getElementsByTagName("MiniMap");
        Node miniMapNode;
        for (int i = 0; i < miniMapNodes.getLength(); i++) {
            miniMapNode = miniMapNodes.item(i);
            if (miniMapNode.getNodeName().equals("MiniMap")) {
                nameMatcher = attrExtractor.matcher(miniMapNode.getAttributes().getNamedItem("enabled").toString());
                if (nameMatcher.find() && nameMatcher.group(1).equals("no")) {
                    this.disableMiniMap();
                }
            }
        }

        NodeList typeNodes = root.getElementsByTagName("Typeblocking");
        Node typeNode;
        for (int i = 0; i < typeNodes.getLength(); i++) {
            typeNode = typeNodes.item(i);
            if (typeNode.getNodeName().equals("Typeblocking")) {
                nameMatcher = attrExtractor.matcher(typeNode.getAttributes().getNamedItem("enabled").toString());
                if (nameMatcher.find() && nameMatcher.group(1).equals("no")) {
                    this.enableTypeBlocking(false);
                } else {
                    this.enableTypeBlocking(true);
                }
            }
        }
    }

    /**
     * Clears the Workspace of: - all the live blocks in the BlockCanvas. - all
     * the pages on the BlockCanvas - all its BlockDrawers and the RB's that
     * reside within them - clears all the BlockDrawer bars of its drawer
     * references and their associated buttons - clears all RenderableBlock
     * instances (which clears their associated Block instances.) Note: we want
     * to get rid of all RendereableBlocks and their references.
     * <p>
     * Want to get the Workspace ready to load another workspace
     */
    public void reset() {
        //we can't iterate and remove widgets at the same time so
        //we remove widgets after we've collected all the widgets we want to remove
        //TreeSet.remove() doesn't always work on the TreeSet, so instead,
        //we clear and re-add the widgets we want to keep
        ArrayList<WorkspaceWidget> widgetsToRemove = new ArrayList<WorkspaceWidget>();
        ArrayList<WorkspaceWidget> widgetsToKeep = new ArrayList<WorkspaceWidget>();
        for (WorkspaceWidget w : workspaceWidgets) {
            if (w instanceof Page) {
                widgetsToRemove.add(w);
            } else {
                widgetsToKeep.add(w);
            }
        }
        workspaceWidgets.clear();
        workspaceWidgets.addAll(widgetsToKeep);
        workspaceWidgets.add(factory);

        //We now reset the widgets we removed.
        //Doing this for each one gets costly.
        //Do not do this for Pages because on repaint,
        //the Page tries to access its parent.
        for (WorkspaceWidget w : widgetsToRemove) {
            Container parent = w.getJComponent().getParent();
            if (w instanceof Page) {
                ((Page) w).reset();
            }
            if (parent != null) {
                parent.remove(w.getJComponent());
                parent.validate();
                parent.repaint();
            }
        }

        //We now reset, the blockcanvas, the factory, and the renderableblocks
        blockCanvas.reset();
        addPageAt(Page.getBlankPage(this), 0, false); //TODO: System expects PAGE_ADDED event
        factory.reset();

        env.resetAll();

        revalidate();
    }

    /**
     * *********************************
     * State Saving Stuff for Undo/Redo * *********************************
     * @param menu
     */
    public void setActiveCPopupMenu(CPopupMenu menu) {
        activeMenu = menu;
    }

    /**
     *
     */
    public void deactiveCPopupMenu() {
        if (activeMenu != null) {
            activeMenu.superSetVisible(false);
            activeMenu = null;
        }
    }

    /**
     *
     * @param wid
     */
    public void setActiveWidget(LabelWidget wid) {
        activeWidget = wid;
    }

    /**
     *
     */
    public void deactiveLabelWidget() {
        if (activeWidget != null) {
            activeWidget.setEditingState(false);
        }
    }

    private class WorkspaceState {

        public Map<Long, Object> blockStates;
        public Object blockCanvasState;
    }

    /**
     *
     * @return
     */
    public Object getState() {
        return null;
    }

    /**
     *
     * @param memento
     */
    public void loadState(Object memento) {
        assert memento instanceof WorkspaceState : "";
        if (memento instanceof WorkspaceState) {
            WorkspaceState state = (WorkspaceState) memento;
            //Load the blocks state
            for (Long blockID : state.blockStates.keySet()) {
                Block toBeUpdated = getEnv().getBlock(blockID);
                toBeUpdated.loadState(state.blockStates.get(blockID));
            }
            //Load the canvas state
            blockCanvas.loadState(state.blockCanvasState);
        }
    }

    /**
     *
     */
    public void undo() {
    }

    /**
     *
     */
    public void redo() {
    }

    /**
     * ****************************************
     * RBParent implemented methods ****************************************
     */
    public void addToBlockLayer(Component c) {
        this.add(c, DRAGGED_BLOCK_LAYER);
    }

    public void addToHighlightLayer(Component c) {
        this.add(c, DRAGGED_BLOCK_HIGHLIGHT_LAYER);
    }
}
