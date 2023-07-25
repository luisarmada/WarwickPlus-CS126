package stores;

import interfaces.IKeywords;
import structures.MyArrayList;

public class Keywords implements IKeywords{

    private MyArrayList<Integer> id;
    private MyArrayList<Keyword[]> keywords;
    private MyArrayList<Keyword> uniqueKeywords;

    public Keywords() {
        id = new MyArrayList<>();
        keywords = new MyArrayList<>();
        uniqueKeywords = new MyArrayList<>();
    }

    @Override
    public boolean add(int id, Keyword[] keyword) {
        boolean result = this.id.add(id);
        result &= this.keywords.add(keyword);

        for (int i = 0; i < keyword.length; i++) {
            boolean found = false;
            for (int j = 0; j < uniqueKeywords.size(); j++) {
                if (keyword[i].getID() == uniqueKeywords.get(j).getID()) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                uniqueKeywords.add(keyword[i]);
            }
        }

        return result;
    }

    @Override
    public boolean addKeyword(int id, Keyword keyword) {
        boolean result = true;

        for (int i = 0; i < this.id.size(); i++) {
            if(this.id.get(i) == id) {
                Keyword[] tmp = new Keyword[keywords.get(i).length + 1];
                for (int j = 0; j < keywords.get(i).length; j++) {
                    tmp[j] = keywords.get(i)[j];
                }
                tmp[keywords.get(i).length] = keyword;
                keywords.set(i, tmp);
                return result;
            }
        }

        result &= this.id.add(id);
        Keyword[] tmp = {keyword};
        result &= keywords.add(tmp);
        return result;
    }

    @Override
    public boolean addKeyword(int id, Keyword[] keywords) {
        boolean result = true;

        for (int i = 0; i < this.id.size(); i++) {
            if (this.id.get(i) == id) {
                Keyword[] tmp = new Keyword[this.keywords.get(i).length + keywords.length];
                for (int j = 0; j < this.keywords.get(i).length; j++) {
                    tmp[j] = this.keywords.get(i)[j];
                }
                for (int j = 0; j < keywords.length; j++){
                    tmp[this.keywords.get(i).length+j] = keywords[j];
                }
                this.keywords.set(i, tmp);
                return result;
            }
        }

        result &= this.id.add(id);
        result &= this.keywords.add(keywords);
        return result;
    }

    @Override
    public boolean remove(int id) {
        int index = this.id.indexOf(id);
        boolean result = this.id.remove(id);
        result &= this.keywords.remove(this.keywords.get(index));
        return result;
    }

    @Override
    public boolean removeKeyword(int id, Keyword keyword) {
        for (int i = 0; i < this.id.size(); i++) {
            if(this.id.get(i) == id) {
                int indexToRemove = -1;
                for (int j = 0; j < this.keywords.get(i).length; j++) {
                    if(this.keywords.get(i)[j].compareTo(keyword) == 0) {
                        indexToRemove = j;
                        break;
                    }
                }
                if (indexToRemove >=0) {
                    Keyword[] tmp = new Keyword[this.keywords.get(i).length-1];
                    int counter = 0;
                    for (int j = 0; j < this.keywords.get(i).length; j++) {
                        if (j == indexToRemove) {
                            continue;
                        }
                        tmp[counter] = this.keywords.get(i)[j];
                        counter++;
                    }

                    this.keywords.set(i, tmp);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] getIDs() {
        int[] result = new int[id.size()];
        for (int i = 0; i < id.size(); i++) {
            result[i] = id.get(i);
        }
        return result;
    }

    @Override
    public int[] getFilmsWithKeyword(Keyword keyword) {
        MyArrayList<Integer> tmp = new MyArrayList<>();
        for (int i = 0; i < this.id.size(); i++) {
            for (int j = 0; j < this.keywords.get(i).length; j++) {
                if (keyword.compareTo(this.keywords.get(i)[j]) == 0) {
                    tmp.add(this.id.get(i));
                    break;
                }
            }
        }

        int[] result = new int[tmp.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = tmp.get(i);
        }
        return result;
    }

    @Override
    public Keyword[] getKeywordsForFilm(int id) {
        return keywords.get(this.id.indexOf(id));
    }

    @Override
    public Keyword[] getUnique() {
        Keyword[] result = new Keyword[uniqueKeywords.size()];
        for (int i = 0; i < uniqueKeywords.size(); i++) {
            result[i] = uniqueKeywords.get(i);
        }
        return result;
    }

    @Override
    public int size() {
        return keywords.size();
    }

    @Override
    public Keyword[] findKeywords(String keyword) {
        MyArrayList<Keyword> tmpResult = new MyArrayList<>();
        for (int i = 0; i < keywords.size(); i++) {
            for (int j = 0; j < keywords.get(i).length; j++) {
                if (keywords.get(i)[j].getName().contains(keyword)) {
                    tmpResult.add(keywords.get(i)[j]);
                }
            }
        }

        Keyword[] result = new Keyword[tmpResult.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = tmpResult.get(i);
        }

        return result;
    }
    
}
