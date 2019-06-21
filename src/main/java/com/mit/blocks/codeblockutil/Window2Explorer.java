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
    
    public static int indexOfCanvas;
    
    //var for knoving current canvas in method set drawers card in kesha code
    private int currentCanvas=0;
    boolean was=false;
    boolean getedParent=false;
    
    public static Map<String,JComponent> dictionary = new HashMap<String,JComponent>();
    public static JComponent currentDir,canvasPanel;
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
        //buttonPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(55, 150, 240)));
        buttonPane.setBackground(Color.black);
        this.add(canvasPane, BorderLayout.CENTER);
        buttonPane.setLayout(new BorderLayout());//VerticalLayout());//GridLayout(0, 2));
        this.add(buttonPane, BorderLayout.NORTH);
        
        this.currentDir = this.canvasPane;
        this.cdir = this;
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
    public void setDrawersCard(List<? extends Canvas> items) {
        canvases.clear();
        buttonPane.removeAll();
        int size = items.size();
        if (size % 2 == 1) {
            size++;
        }
        size = buttonHeight * 24;
        buttonPane.setPreferredSize(new Dimension(60, size));
        JPanel butpan = new JPanel(new VerticalLayout());
        butpan.setBackground(Color.white);
        for (int i = 0; i < items.size(); i++) {
            final int index = i;
            Canvas item = items.get(i);
            //final CButton button = new CButton(item.getColor(), item.getColor().brighter().brighter().brighter(),item.getName());
            item.getJComponent().setBackground(new Color(236, 236, 236));
            CButton button = new RMenuButton(item.getName(), item.getColor());
            button.setPreferredSize(new Dimension(30, 35));
            JComponent scrollFLVL = new RHoverScrollPane(
                    item.getJComponent(),
                    ScrollPolicy.VERTICAL_BAR_AS_NEEDED,
                    ScrollPolicy.HORIZONTAL_BAR_AS_NEEDED,
                    15, item.getColor(), Color.darkGray);
            final JComponent blockCanvasWithDepth = new JLayeredPane();
            blockCanvasWithDepth.setPreferredSize(new Dimension(300, 500));
            blockCanvasWithDepth.add(scrollFLVL, 0);
            URL iconURL = Workspace.class.getClassLoader().getResource("com/ardublock/trashcan.png");
            Image imageRaw = new ImageIcon(iconURL).getImage().getScaledInstance(
                    84, 84, java.awt.Image.SCALE_SMOOTH);
            ImageIcon button_icon = new ImageIcon(imageRaw);
            //ImageIcon button_icon = new ImageIcon(iconURL).getImage().getScaledInstance(128,128, java.awt.Image.SCALE_SMOOTH);
            final JLabel mainLogo = new JLabel(button_icon);
            basketPane = new JPanel(new BorderLayout());
            basketPane.add(mainLogo, BorderLayout.CENTER);
            blockCanvasWithDepth.add(basketPane, 1);
            blockCanvasWithDepth.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    super.componentResized(e);
                    blockCanvasWithDepth.getComponents()[0].setBounds(0, 0, blockCanvasWithDepth.getWidth() - 1, blockCanvasWithDepth.getHeight() - 1);
                    blockCanvasWithDepth.getComponents()[1].setBounds(0, 0, blockCanvasWithDepth.getWidth() - 1, blockCanvasWithDepth.getHeight() - 1);

                }
            });
            blockCanvasWithDepth.getComponents()[1].setVisible(false);
            canvases.add(blockCanvasWithDepth);
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    selectCanvas(index);
                }
            });
            butpan.add(button);

        }
        RHoverScrollPane buttonScroll = new RHoverScrollPane(
                butpan,
                ScrollPolicy.VERTICAL_BAR_AS_NEEDED,
                ScrollPolicy.HORIZONTAL_BAR_NEVER,
                15, new Color(255, 133, 8), Color.darkGray);//new JScrollPane(butpan);
        //buttonScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        buttonPane.add(buttonScroll, BorderLayout.CENTER);
        if (!canvases.isEmpty()) {
            canvasPane.add(canvases.get(0));
            
           
            
        }
        
        
        List<JComponent> mycanvases = canvases;
        RHoverScrollPane myc2;
        if(mycanvases.size()>0){
            JComponent currentCanvas = (JComponent)(mycanvases.get(this.currentCanvas).getComponents()[0]);
            myc2=(RHoverScrollPane)currentCanvas;
            int siz = myc2.getComponents().length;
            JComponent tc =(JComponent) myc2.getComponent(2);
            currentCanvas=tc;
            
              
            currentCanvas=(JComponent)tc.getComponent(0);
            
            //geted jviewport
            JComponent mc6=currentCanvas;
            
            JComponent listcpnts=(JComponent)mc6.getComponent(0);
            
            //geted var with list with blocks
            currentCanvas=listcpnts;
                        JComponent blocksCanvas = currentCanvas;

            
            //FactoryRenderableBlock
            int onebl = (int)blocksCanvas.getComponents().length;
            
            for(int i=0;i<blocksCanvas.getComponentCount();i+=2){
            FactoryRenderableBlock bl;
            if(onebl>0){
                FactoryRenderableBlock bliner=(FactoryRenderableBlock)blocksCanvas.getComponent(i);
                 bl=bliner;
                 String kye = bl.getKeyword();
                 this.dictionary.put(bl.getKeyword(),bl);
            }
            }
            
            if(this.was){
            this.currentCanvas++;
            
            
            }
            this.was=true;
        }
//        RHoverScrollPane rh =(RHoverScrollPane)myc.getComponent(2);
//        JViewport vp = (JViewport)rh.getComponent(0);
        //FactoryCanvas fc = (FactoryCanvas)vp.getComponent(0);
        
        
        
        this.revalidate();
    }
    
    
    //kesha code end
    public static JComponent getCurrentDir(){
        
        
        
        return null;
    }

    public void selectCanvas(int index) {
        oldIndex = index;
        if (index >= 0 && index < canvases.size()) {
            JComponent scroll = canvases.get(index);
            canvasPane.removeAll();
            canvasPane.add(scroll);
            canvasPane.revalidate();
            canvasPane.repaint();
            this.canvasPanel = canvasPane;
            this.currentDir=scroll;
            this.indexOfCanvas=index;
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
        JComponent scroll = canvases.get(oldIndex);
        scroll.getComponents()[0].setVisible(false);
        scroll.getComponents()[1].setVisible(true);
        canvasPane.removeAll();
        canvasPane.add(scroll);
        canvasPane.revalidate();
        canvasPane.repaint();

    }

    public void deactiveBasket() {
        JComponent scroll = canvases.get(oldIndex);
        scroll.getComponents()[0].setVisible(true);
        scroll.getComponents()[1].setVisible(false);
        canvasPane.removeAll();
        canvasPane.add(scroll);
        canvasPane.revalidate();
        canvasPane.repaint();
    }

    /**
     * @return a JCOmponent representation of this explorer
     */
    public JComponent getJComponent() {
        return this;
    }
}
