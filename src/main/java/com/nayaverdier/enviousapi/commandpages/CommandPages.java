package com.nayaverdier.enviousapi.commandpages;

import java.util.ArrayList;
import java.util.List;

public class CommandPages
{
    private List<List<String>> pages;
    private int                pageSize;

    public CommandPages(int pageSize, String... lines)
    {
        this.pageSize = pageSize;
        this.pages = new ArrayList<>();
        add(lines);
    }

    public int getTotalPages()
    {
        return pages.size();
    }

    public List<String> getPage(int page)
    {
        if (page >= pages.size())
            return null;
        return pages.get(page);
    }

    public String getLine(int pageNumber, int lineNumber)
    {
        if (pageNumber >= pages.size())
            return null;
        List<String> page = pages.get(pageNumber);
        if (lineNumber >= page.size())
            return null;
        return page.get(lineNumber);
    }

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