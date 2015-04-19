package com.nayaverdier.enviousapi.commandpages;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2015 nverdier
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF
 * OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class CommandPages
{
    private List<List<String>> pages;
    private int pageSize;

    /**
     * Creates a new CommandPages with specified page size and String...
     *
     * @param pageSize The size for each page to be
     * @param lines    The lines that are initially added to the CommandPages
     */
    public CommandPages(int pageSize, String... lines)
    {
        this.pageSize = pageSize;
        this.pages = new ArrayList<>();
        add(lines);
    }

    /**
     * Gets the amount of pages that are in the CommandPages
     *
     * @return The amount of pages in the CommandPages
     */
    public int getTotalPages()
    {
        return pages.size();
    }

    /**
     * Gets the page at the specified index
     *
     * @param page The page number to get
     * @return The page at the specified index
     */
    public List<String> getPage(int page)
    {
        if (page >= pages.size())
            return null;
        return pages.get(page);
    }

    /**
     * Gets the final page of the CommandPages
     *
     * @return The final page of the CommandPages
     */
    public List<String> getLastPage()
    {
        return getPage(getTotalPages() - 1);
    }

    /**
     * Gets the String at the specified page and line numbers
     *
     * @param pageNumber The page to retrieve the String from
     * @param lineNumber The line to retrieve the String from
     * @return The String at specified page and line
     */
    public String getLine(int pageNumber, int lineNumber)
    {
        if (pageNumber >= pages.size())
            return null;
        List<String> page = pages.get(pageNumber);
        if (lineNumber >= page.size())
            return null;
        return page.get(lineNumber);
    }

    /**
     * Adds a String... to the CommandPages
     *
     * @param lines The lines to add
     */
    public void add(String... lines)
    {
        for (String s : lines)
        {
            addString(s);
        }
    }

    private void addString(String s)
    {
        if (pages.size() == 0 || pages.get(pages.size() - 1).size() == pageSize)
        {
            pages.add(new ArrayList<String>());
        }
        pages.get(pages.size() - 1).add(s);
    }
}