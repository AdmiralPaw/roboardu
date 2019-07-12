package com.mit.blocks.codeblockutil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.ardublock.ui.OpenblocksFrame;
import com.mit.blocks.codeblockutil.CScrollPane.ScrollPolicy;
import com.mit.blocks.renderable.FactoryRenderableBlock;
import com.mit.blocks.workspace.SearchBar;
import com.mit.blocks.workspace.SearchableContainer;
import com.mit.blocks.workspace.Workspace;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import org.jfree.ui.tabbedui.VerticalLayout;

/**
 * A WindowExplorer is an Explorer that explores its set of canvases by
 * displaying a panel of tabs on the top or bottom . In the center is the
 * current active canvas. When a user presses a tab, the corresponding canvas
 * changes to reflect the new active canvas.
 *
 * @author An Ho
 *
 */
public class Window2Explorer extends JPanel implements Explorer {

    //TODO: добавить поиск - удаление не соответсвующих элементов в тек директории(компоненте)
    //и добавление в него компонентов из переменной dictionary соответствующих блоков
    public static JComponent cdir;


    public static Map<String, JComponent> dictionary = new HashMap<String, JComponent>();
    public static JComponent currentDir, canvasPanel;
    //public static JComponent currentCanvas;

    private static final long serialVersionUID = 328149080308L;
    private static final int buttonHeight = 13;
    /**
     * The set of drawers that wraps each canvas
     */
    public static List<JComponent> canvases;
    /**
     * Teh canvas portion
     */
    private JPanel canvasPane;
    /**
     * The tab portion
     */
    public JPanel buttonPane;
    public JPanel basketPane;
    public JLayeredPane currentCanvasWithBasket;
    public int oldIndex;

    /**
     * Constructs new stack explorer
     */
    public Window2Explorer() {
        super();
        this.setLayout(new BorderLayout());
        this.canvases = new ArrayList<JComponent>();
        this.canvasPane = new JPanel(new BorderLayout());
        this.buttonPane = new JPanel();
        buttonPane.setBackground(Color.black);
        this.add(canvasPane, BorderLayout.CENTER);
        buttonPane.setLayout(new BorderLayout());//VerticalLayout());//GridLayout(0, 2));
        this.add(buttonPane, BorderLayout.NORTH);

        this.currentDir = this.canvasPane;
        this.cdir = this;

        this.canvasPanel = this.canvasPane;
        currentCanvasWithBasket = new JLayeredPane();

        URL iconURL = Workspace.class.getClassLoader().getResource("com/ardublock/trashcan.png");
        Image imageRaw = new ImageIcon(iconURL).getImage().getScaledInstance(
                84, 84, java.awt.Image.SCALE_SMOOTH);
        ImageIcon button_icon = new ImageIcon(imageRaw);
        //ImageIcon button_icon = new ImageIcon(iconURL).getImage().getScaledInstance(128,128, java.awt.Image.SCALE_SMOOTH);
        final JLabel mainLogo = new JLabel(button_icon);
        basketPane = new JPanel(new BorderLayout());
        basketPane.add(mainLogo, BorderLayout.CENTER);
        basketPane.setVisible(false);
        
        currentCanvasWithBasket.add(basketPane, new Integer(1));
        currentCanvasWithBasket.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                for (int i = 0; i < currentCanvasWithBasket.getComponentCount(); i++) {
                    currentCanvasWithBasket.getComponents()[i].setBounds(0, 0, currentCanvasWithBasket.getWidth() - 1, currentCanvasWithBasket.getHeight() - 1);
                }
            }
        });
        canvasPane.add(currentCanvasWithBasket);

    }

    public boolean anyCanvasSelected() {
        return false;
    }

    public int getSelectedCanvasWidth() {
        return this.canvasPane.getWidth();
    }

    public void addListener(ExplorerListener gel) {
    }

    public void removeListener(ExplorerListener gel) {
    }

    /**
     * Reassigns the set of canvases that this explorer controls. Though the
     * collection of canvas mnay be empty, it may not be null.
     *
     * @param items
     *
     * @requires items != null && for each element in item, element!= null
     */
    int iterationCount = 0;
    public void setDrawersCard(List<? extends Canvas> items) {
        iterationCount++;
        canvases.clear();
        buttonPane.removeAll();
        int size = buttonHeight * 24;
        buttonPane.setPreferredSize(new Dimension(60, size));
        JPanel butpan = new JPanel(new VerticalLayout());
        butpan.setBackground(Color.white);
        for (int i = 0; i < items.size(); i++) {
            final int index = i;
            Canvas item = items.get(i);
            item.getJComponent().setBackground(new Color(236, 236, 236));
            CButton button = new RMenuButton(item.getName(), item.getColor());
            button.setPreferredSize(new Dimension(30, 35));
            JComponent scroll = new RHoverScrollPane(
                    item.getJComponent(),
                    ScrollPolicy.VERTICAL_BAR_AS_NEEDED,
                    ScrollPolicy.HORIZONTAL_BAR_AS_NEEDED,
                    15, item.getColor(), Color.darkGray);

            canvases.add(scroll);
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    selectCanvas(index);
                    deactiveBasket();
                }
            });
            butpan.add(button);

        }
        RHoverScrollPane buttonScroll = new RHoverScrollPane(
                butpan,
                ScrollPolicy.VERTICAL_BAR_AS_NEEDED,
                ScrollPolicy.HORIZONTAL_BAR_NEVER,
                15, new Color(255, 133, 8), Color.darkGray);
        buttonPane.add(buttonScroll, BorderLayout.CENTER);
        if (!canvases.isEmpty()) {
            selectCanvas(0);

        }
        if(iterationCount==16) {
            long time1 = System.currentTimeMillis();
            for (Canvas unit : items) {
                for (Component comp : unit.getJComponent().getComponents()) {
                    if (comp instanceof FactoryRenderableBlock) {

                        FactoryRenderableBlock bl = ((FactoryRenderableBlock) comp).deepClone();

                        boolean contains = bl.getKeyword().contains("(");
                        if (contains) {
                            int index = bl.getKeyword().indexOf("(");
                            //System.out.println(bl.getKeyword().toUpperCase().substring(0,index));
                            dictionary.put(bl.getKeyword().toUpperCase().substring(0, index), bl);
                        } else {
                            //System.out.println(bl.getKeyword().toUpperCase());
                            dictionary.put(bl.getKeyword().toUpperCase(), bl);
                        }
                    }
                }
            }
            iterationCount = 0;
        }
        this.revalidate();
    }


    public void selectCanvas(int index) {
        oldIndex = index;
        if (index >= 0 && index < canvases.size()) {
            
            JComponent scroll = canvases.get(index);
            try {
                currentCanvasWithBasket.remove(currentCanvasWithBasket.getComponentsInLayer(0)[0]);
            } catch (Exception e) {
            }

            currentCanvasWithBasket.add(scroll, new Integer(0));
            currentCanvasWithBasket.setBounds(new Rectangle(0, 0, 300, 300));
        }
    }

    /**
     * Reforms this explorer based on the new size or location of this explorer.
     * For some explorers whose implementation does not depend on the size of
     * itself, this method may trigger no action.
     */
    public void reformView() {
    }

    public void activeBasket() {
        
        currentCanvasWithBasket.getComponentsInLayer(0)[0].setVisible(false);
        currentCanvasWithBasket.getComponentsInLayer(1)[0].setVisible(true);

    }

    public void deactiveBasket() {
        currentCanvasWithBasket.getComponentsInLayer(0)[0].setVisible(true);
        currentCanvasWithBasket.getComponentsInLayer(1)[0].setVisible(false);
    }

    public void setSearchResult(JComponent panel) {
        currentCanvasWithBasket.remove(currentCanvasWithBasket.getComponentsInLayer(0)[0]);
        currentCanvasWithBasket.add(panel, new Integer(0));
        currentCanvasWithBasket.setBounds(new Rectangle(0, 0, 300, 300));
        currentCanvasWithBasket.getComponentsInLayer(0)[0].setVisible(true);
        
    }
    
    public void setNormalMode()
    {
        selectCanvas(oldIndex);
    }

    /**
     * @return a JCOmponent representation of this explorer
     */
    public JComponent getJComponent() {
        return this;
    }
}
