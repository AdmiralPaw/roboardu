package com.mit.blocks.workspace;

import com.mit.blocks.codeblockutil.*;
import com.mit.blocks.renderable.FactoryRenderableBlock;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Данный класс добавляет компонент панели поиска в графический интерфейс CodeBlocks, который позволяет
 * пользователю находить объекты поиска, такие как блоки в ящиках и рабочей области, с запросом по имени.
 */
public class SearchBar {

    private static JComponent fcdir;

    /**
     *
     */
    public static Workspace workspace;

    /**
     *
     */
    public final RQueryField searchPanel;
    private final JTextField searchBar;
    private final String defaultText;
    private Set<SearchableContainer> containerSet = new HashSet<SearchableContainer>();
    private Map<SearchableContainer, Set<SearchableElement>> searchResults = new HashMap<SearchableContainer, Set<SearchableElement>>();
    private Timer searchUpdater;
    private static final int SEARCH_UPDATER_DELAY = 5000;
    private Timer searchThrottle;
    private static final int SEARCH_THROTTLE_DELAY = 250;
    
    private ArrayList<Long> LastSearch = new ArrayList<>(); 

    private Map<String, JComponent> dict = new HashMap<String, JComponent>();

    private enum SearchRange {

        CHECK_ALL, REMOVE_FROM_FOUND, ADD_FROM_NOT_FOUND
    }

    private SearchRange searchRange;

    /**
     * Contructs a new search bar.
     *
     * @param defaultText      the text to show when the user is not using the search
     *                         bar, such as "Search blocks"
     * @param tooltip          the text to show as a tooltip for the search bar when the
     *                         user hovers the mouse over the search bar.
     * @param defaultComponent the component for which focus should be requested
     *                         if the user presses the Escape key while using the search bar.
     */
    public SearchBar(String defaultText, String tooltip, final Workspace defaultComponent) {
        this.dict = Window2Explorer.dictionary;
        workspace = defaultComponent;
        this.defaultText = defaultText;
        this.searchPanel = new RQueryField();
        this.searchBar = this.searchPanel.getQueryField();
        Insets insets = searchBar.getInsets();
        searchBar.setBorder(BorderFactory.createEmptyBorder(insets.top, 10, insets.bottom, insets.right));
        searchBar.setToolTipText(tooltip);
        searchBar.setColumns(12);

        resetSearchBar();
        InputMap imap = searchBar.getInputMap(JTextField.WHEN_IN_FOCUSED_WINDOW);
        KeyStroke searchBarStr = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK);
        imap.put(searchBarStr, "search");
        searchBar.getActionMap().put("search", new ClickAction(this));
        searchBar.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                readySearchBar();
            }

            public void focusLost(FocusEvent e) {
                resetSearchBar();
            }
        });

        searchBar.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    searchBar.setText("");
                    defaultComponent.requestFocusInWindow();
                }
            }
        });

        searchBar.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                // This method intentionally left blank.
            }

            public void insertUpdate(DocumentEvent e) {

                String text = searchBar.getText();
                if (text.equals(SearchBar.this.defaultText)) {
                    return;
                }
                // If the search term changed only at the beginning or end, then only
                // the blocks found already may change.  Remove unmatched blocks from
                // foundBlocks.
                if (e.getOffset() == 0 || e.getOffset() + e.getLength() == text.length()) {
                    performSearch(SearchRange.REMOVE_FROM_FOUND);

                    searchBlocks();

                    //set rbHighlightBlock position as block position
                } else {
                    // If the search term changed in the middle, then the blocks found and
                    // the blocks yet to be found may have changed.  Recheck all blocks.
                    performSearch(SearchRange.CHECK_ALL);
                }

            }

            private void searchBlocks() {
                
                
                
                List<FactoryRenderableBlock> searchedBlocks = getBlocks(searchBar.getText().toUpperCase());
                FactoryCanvas fcanvas = new FactoryCanvas("searched canvas", Color.WHITE);
                fcanvas.setBackground(new Color(236, 236, 236));

                for (FactoryRenderableBlock block : searchedBlocks) {
                    FactoryRenderableBlock newBlock = block.deepClone();
                    LastSearch.add(newBlock.getBlockID());
                    newBlock.OneSetZoomLevel(1);
                    fcanvas.addBlock(newBlock);

                }
                fcanvas.layoutBlocks();
                JComponent scroll = new RHoverScrollPane(
                        fcanvas,
                        CScrollPane.ScrollPolicy.VERTICAL_BAR_AS_NEEDED,
                        CScrollPane.ScrollPolicy.HORIZONTAL_BAR_AS_NEEDED,
                        15, Color.BLACK, Color.darkGray);
                // Window2Explorer.canvasPanel.repaint();
                List<Explorer> exList = workspace.getFactoryManager().getNavigator().getExplorers();
                for (Explorer ex : exList) {
                    if (ex instanceof Window2Explorer) {
                        ((Window2Explorer) ex).setSearchResult(scroll);
                    }
                }
//                    dir.validate();
//                    dir.repaint();                
            }

            //get blocks which partly matches with text in searchbar
            private List<FactoryRenderableBlock> getBlocks(String text) {
                List<FactoryRenderableBlock> blocks = new ArrayList<FactoryRenderableBlock>() {
                };
                Map<String, JComponent> data = Window2Explorer.dictionary;
                text = text.toUpperCase();
                Set<String> names = data.keySet();
                for (int i = 0; i < names.size(); i++) {
                    if (Pattern.matches(".*" + text + ".*", (CharSequence) names.toArray()[i])) {
                        try {
                            blocks.add(((FactoryRenderableBlock) data.get(names.toArray()[i])).clone());
                        } catch (Exception e) {

                        }
                    }
                }
                return blocks;
            }

            private JComponent getCurrentPanel() {
                JComponent component = (JComponent) Window2Explorer.cdir.getComponent(0);
//                component = (JComponent) Window2Explorer.cdir.getComponent(0);
//                component = (JComponent) Window2Explorer.cdir.getComponent(2);
//                component = (JComponent) Window2Explorer.cdir.getComponent(0);
//                component = (JComponent) Window2Explorer.cdir.getComponent(0);
                SearchBar.fcdir = component;
                return component;
            }

            public void removeUpdate(DocumentEvent e) {

                if (searchBar.getText().equals("")) {
                    List<Explorer> exList = workspace.getFactoryManager().getNavigator().getExplorers();
                    for (Explorer ex : exList) {
                        if (ex instanceof Window2Explorer) {
                            ((Window2Explorer) ex).setNormalMode();
                        }
                    }

                } else {
                    searchBlocks();
                }

                if (searchBar.getText().equals("")) {
                    performSearch(SearchRange.CHECK_ALL);

                    List<Explorer> exList = workspace.getFactoryManager().getNavigator().getExplorers();
                    for (Explorer ex : exList) {
                        if (ex instanceof Window2Explorer) {
                            ((Window2Explorer) ex).setNormalMode();
                        }
                    }

                } else if (e.getOffset() == 0 || e.getOffset() == searchBar.getText().length()) {
                    // If the search term changed only at the beginning or end, then
                    // the blocks found already do not change.  Check for additional blocks
                    // from the Worspace.
                    performSearch(SearchRange.ADD_FROM_NOT_FOUND);
                } else {
                    // If the search term changed in the middle, then the blocks found may have
                    // changed.  Recheck all blocks.
                    performSearch(SearchRange.CHECK_ALL);

                    searchBlocks();

                }
            }
        });

        // Repeat search periodically to refresh results in case elements change,
        // such as when a new block is dragged onto the Workspace and should be included in the results.
        searchUpdater = new Timer(SEARCH_UPDATER_DELAY, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // Skip the update if the throttle is about to perform a search anyway.
                if (searchThrottle != null && !searchThrottle.isRunning()) {
                    searchRange = SearchRange.CHECK_ALL;
                    performSearchTimerHandler();
                }
            }
        });
        searchUpdater.start();
    }

    /**
     * Returns the Swing component representation of the search bar.
     *
     * @return the Swing the component representation of the search bar.
     */
    public JComponent getComponent() {
        return searchPanel;
    }

    /**
     * Returns a set of elements representing the search results for a
     * particular container.
     *
     * @param container the returned search elements will be from this search
     *                  container
     * @return search results for a particular container
     */
    public Iterable<SearchableElement> getSearchResults(SearchableContainer container) {
        Set<SearchableElement> results = searchResults.get(container);
        if (results == null) {
            results = Collections.emptySet();
            return results;
        }
        return Collections.unmodifiableSet(results);
    }

    /**
     * Adds a searchable to the set of searchables queried by this search bar.
     * If more than one search bar exists, the same searchable should not be
     * added to more than search bar.
     *
     * @param searchable the container to add
     */
    public void addSearchableContainer(SearchableContainer searchable) {
        synchronized (this) {
            containerSet.add(searchable);
        }
    }

    /**
     * Removes a searchable container from the set of searchables queried by
     * this search bar.
     *
     * @param searchable the container to remove
     */
    public void removeSearchableContainer(SearchableContainer searchable) {
        synchronized (this) {
            containerSet.remove(searchable);
        }
    }

    /**
     * Clears all the internal data of this.
     */
    public void reset() {
        synchronized (this) {
            searchResults.clear();
            containerSet.clear();
        }
    }

    /**
     * Whenever the search bar loses focus and has an empty document, put
     * "Search blocks" in gray italics.
     */
    private void resetSearchBar() {
        if (searchBar.getText().trim().equals("")) {
            Font font = searchBar.getFont();
            searchBar.setFont(new Font(font.getName(), Font.ITALIC, font.getSize()));
            searchBar.setForeground(Color.GRAY);
            searchBar.setText(defaultText);
            if(LastSearch.size()!=0){
                    LastSearch.forEach(rb ->{
                        workspace.getEnv().getRenderableBlock(rb).removeBlock();
                    });
                    LastSearch.clear();
                }
        }
    }

    /**
     * Whenever the search bar gains focus, if the text is "Search Blocks", then
     * clear the contents and reset the font. Otherwise, highlight whatever is
     * there.
     */
    private void readySearchBar() {
        if (defaultText.equals(searchBar.getText())) {
            searchBar.setText("");
            Font font = searchBar.getFont();
            searchBar.setFont(new Font(font.getName(), Font.PLAIN, font.getSize()));
            searchBar.setForeground(Color.BLACK);
        } else {
            searchBar.selectAll();
        }
    }

    /**
     * Clears all search results from a previous query.
     */
    private void clearSearchResults() {
        for (Set<SearchableElement> foundElements : searchResults.values()) {
            for (SearchableElement element : foundElements) {
                element.updateInSearchResults(false);
            }
            for (SearchableContainer container : searchResults.keySet()) {
                container.updateContainsSearchResults(false);
            }
        }
        searchResults.clear();
    }

    /**
     * Perform a new search for the specified range based on updates to the
     * search bar.
     *
     * @param range verifies the optimization for search depending on whether
     *              the search space has become bigger or smaller since the last search.
     */
    private void performSearch(final SearchRange range) {
        // If new requests to search come in during the delay, reset the timer and update the range.
        // If the range changed from the previous request since starting the timer,
        // automatically do a CHECK_ALL.
        if (searchRange == null) {
            searchRange = range;
        }
        if (!searchRange.equals(range)) {
            searchRange = SearchRange.CHECK_ALL;
        }
        if (searchThrottle == null) {
            searchThrottle = new Timer(SEARCH_THROTTLE_DELAY, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    performSearchTimerHandler();
                }
            });
            searchThrottle.setRepeats(false);
        }
        if (searchThrottle.isRunning()) {
            searchThrottle.restart();
        } else {
            searchThrottle.start();
        }
    }

    private void performSearchTimerHandler() {

        if (searchBar.getText().equals("")) {
            clearSearchResults();
            return;
        }
        // Called by a javax.swing.Timer to throttle search by about a quarter second.
        SearchRange range = searchRange;
        searchRange = null;
        Set<SearchableContainer> containers;
        synchronized (this) {
            // Safely grab a copy of the current set of containers to search.
            containers = new HashSet<SearchableContainer>(containerSet);
        }
        if (range == SearchRange.ADD_FROM_NOT_FOUND || range == SearchRange.CHECK_ALL) {
            for (SearchableContainer container : containers) {
                // Update the search results for each container for this query
                Set<SearchableElement> foundElements = searchResults.get(container);
                if (foundElements == null) {
                    foundElements = new HashSet<SearchableElement>();
                    searchResults.put(container, foundElements);
                }
                for (SearchableElement element : container.getSearchableElements()) {
                    if (!foundElements.contains(element) && element.getKeyword().toUpperCase().contains(searchBar.getText().toUpperCase())) {
                        foundElements.add(element);
                        element.updateInSearchResults(true);
                    }
                }
                if (!foundElements.isEmpty()) {
                    container.updateContainsSearchResults(true);
                }
            }
        }
        if (range == SearchRange.REMOVE_FROM_FOUND || range == SearchRange.CHECK_ALL) {
            for (SearchableContainer container : containers) {
                Set<SearchableElement> foundElements = searchResults.get(container);
                if (foundElements != null) {
                    Set<SearchableElement> elementsToRemove = new HashSet<SearchableElement>();
                    for (SearchableElement element : foundElements) {
                        if (!element.getKeyword().toUpperCase().contains(searchBar.getText().toUpperCase())) {
                            elementsToRemove.add(element);
                            element.updateInSearchResults(false);
                        }
                    }
                    foundElements.removeAll(elementsToRemove);
                    if (foundElements.isEmpty()) {
                        container.updateContainsSearchResults(false);
                    }
                }
            }
        }
    }

    class ClickAction extends AbstractAction {
        private SearchBar textField;

        public ClickAction(SearchBar textf) {
            textField = textf;
        }

        public void actionPerformed(ActionEvent e) {
            textField.searchBar.requestFocus();
            textField.readySearchBar();
        }
    }


}
