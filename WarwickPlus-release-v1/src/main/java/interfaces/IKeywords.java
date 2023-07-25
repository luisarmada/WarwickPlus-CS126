package interfaces;

import stores.Keyword;

public interface IKeywords{

    public boolean add(int id, Keyword[] keyword);
    public boolean addKeyword(int id, Keyword keyword);
    public boolean addKeyword(int id, Keyword[] keywords);

    public boolean remove(int id);
    public boolean removeKeyword(int id, Keyword keyword);

    public int[] getIDs();

    public int[] getFilmsWithKeyword(Keyword keyword);

    public Keyword[] getKeywordsForFilm(int id);
    public Keyword[] getUnique();

    public Keyword[] findKeywords(String searchTerm);

    public String toString();
    public int size();
    
}
