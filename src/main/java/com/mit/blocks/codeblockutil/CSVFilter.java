package com.mit.blocks.codeblockutil;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 *
 * @author User
 */
public class CSVFilter extends FileFilter {

    public boolean accept(File file) {
        if (file != null) {
            String filename = file.getName();
            if (filename != null) {
                return filename.endsWith(".csv");
            }
        }
        return false;
    }

    public String getDescription() {
        return "*.csv";
    }
}
