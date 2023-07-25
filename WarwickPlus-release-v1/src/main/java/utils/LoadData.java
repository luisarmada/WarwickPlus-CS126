package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stores.Cast;
import stores.Company;
import stores.Credits;
import stores.Crew;
import stores.Genre;
import stores.Keyword;
import stores.Keywords;
import stores.Movies;
import stores.Ratings;

public class LoadData implements Runnable {

    private enum FileType {CREDITS, KEYWORDS, METADATA, RATINGS}

    private class InputFile {
        private FileType fileType;
        private String fileName;
        private long total = 0;
        private Calendar startTime;
        private Calendar endTime;

        public InputFile(String name, FileType type) {
            fileName = name;
            fileType = type;
            setStartTime();
            setEndTime();
            try {
                Path path = Paths.get(fileName);
                total = (Files.lines(path).count()) -1;
            } catch (Exception e) {
                e.printStackTrace();
                this.toString();
            }
        }

        public String getFileName() {return fileName;}

        public FileType getFileType() {return fileType;}

        public long getTotal() {return total;}

        public void setStartTime() {startTime = Calendar.getInstance();}
        
        public void setEndTime() {endTime = Calendar.getInstance();}

        public long getTimeDifference() {
            return Math.abs(endTime.getTimeInMillis() - startTime.getTimeInMillis());
        }

        @Override
        public String toString() {
            String result = "";
            result = "File name: " + fileName + "\tFile type: ";
            switch (fileType) {
                case CREDITS: result += "CREDITS";
                    break;
                case KEYWORDS: result += "KEYWORDS";
                    break;
                case METADATA: result += "METADATA";
                    break;
                case RATINGS: result += "RATINGS";
                    break;
                default: result += "UNKNOWN";
            }
            result += "\t Total Number of Lines: " + total + "\t Start time: " + startTime.getTime().toString() + "\t End time: " + endTime.getTime().toString() + " (Time taken: " + getTimeDifference() + "ms, Average time per line: " + ((double)getTimeDifference()/(double)total) + "ms)";

            return result;
        }
    };

    private long currentNumber = 0;
    private long totalNumber = 0;
    private String loadingString = "";
    private JProgressBar loadingBar;
    private JLabel loadingText;
    private InputFile[] inputFiles = {new InputFile("data/credits.csv", FileType.CREDITS), new InputFile("data/keywords.csv", FileType.KEYWORDS), new InputFile("data/movies_metadata.csv", FileType.METADATA), new InputFile("data/ratings.csv", FileType.RATINGS)};

    private Credits credits;
    private Keywords keywords;
    private Movies movies;
    private Ratings ratings;

    public LoadData(JProgressBar loadingBar, JLabel loadingText, Credits credits, Keywords keywords, Movies movies, Ratings ratings) {
        this.loadingBar = loadingBar;
        this.loadingText = loadingText;
        this.credits = credits;
        this.keywords = keywords;
        this.movies = movies;
        this.ratings = ratings;
        for (InputFile file : inputFiles) {
            totalNumber+=(file.getTotal()-1);
        }
    }

    @Override
    public void run() {
        for (InputFile file : inputFiles) {
            updateUI(file, 0);
            loadingString = file.getFileName();
            file.setStartTime();
            switch(file.getFileType()) {
                case CREDITS: loadCredits(file);
                    break;
                case KEYWORDS: loadKeywords(file);
                    break;
                case METADATA: loadMetadata(file);
                    break;
                case RATINGS: loadRatings(file);
                    break;
                default: System.err.println("Weird file type...\n\tFile Type: " + file.getFileType() + "\tFile Name: " + file.getFileName());
            }
            updateUI(file, (int)file.getTotal());
            file.setEndTime();
            System.out.println("Processed --> " + file.toString());
        }
        if (loadingText != null) {
            loadingText.setText("Done!");
        }
    }

    public double getPercentage() {
        return ((double)currentNumber/(double)totalNumber);
    }

    public String getLoadingString() {
        return loadingString;
    }

    @Override
    public String toString() {
        String result = "Files:\n";
        for (InputFile file : inputFiles) {
            result += "\t"+file.toString() + "\n";
        }
        result += "Percentage Done: " + (getPercentage()*100) + "% (Number of entries: " + currentNumber + ", Total number of entries: " + totalNumber + ")";

        return result;
    }

    private void loadCredits(InputFile file) {
        System.out.println("\nLoading credits...");
        File creditsCsvFile = new File(file.getFileName());
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                            .setHeader()
                            .setSkipHeaderRecord(true)
                            .build();
        int record_count = 0;
        try (CSVParser parser = CSVParser.parse(creditsCsvFile, Charset.forName("UTF-8"), csvFormat)){
            for (CSVRecord csvRecord : parser){
                if (csvRecord.size() != 3){
                    System.err.println("[" + record_count + "] --CREDITS-- Incorrect number of fields. No of fields found: " + csvRecord.size());
                    currentNumber++;
                    record_count++;
                    updateUI(file, record_count);
                    continue;
                }

                // Parse cast
                JSONArray castJsonArray = new JSONArray(csvRecord.get("cast"));
                Cast[] castArray = new Cast[castJsonArray.length()];
                for (int i = 0; i < castJsonArray.length(); i++){
                    // Each cast member
                    JSONObject castJsonObject = castJsonArray.getJSONObject(i);

                    int castElementId  = castJsonObject.getInt("cast_id");
                    String character   = castJsonObject.getString("character");
                    String creditId    = castJsonObject.getString("credit_id");
                    int gender         = castJsonObject.getInt("gender");
                    int castId         = castJsonObject.getInt("id");
                    String name        = castJsonObject.getString("name");
                    int order          = castJsonObject.getInt("order");
                    String profilePath = castJsonObject.getString("profile_path");

                    castArray[i] = new Cast(castElementId, character, creditId, gender,
                                            castId, name, order, profilePath);
                }

                // Parse crew
                JSONArray crewJsonArray = new JSONArray(csvRecord.get("crew"));
                Crew[] crewArray = new Crew[crewJsonArray.length()];
                for (int i = 0; i < crewJsonArray.length(); i++){
                    // Each crew member 
                    JSONObject crewJsonObject = crewJsonArray.getJSONObject(i);
                    String crewElementId = crewJsonObject.getString("credit_id");
                    String department    = crewJsonObject.getString("department");
                    int gender           = crewJsonObject.getInt("gender");
                    int crewId           = crewJsonObject.getInt("id");
                    String job           = crewJsonObject.getString("job");
                    String name          = crewJsonObject.getString("name");
                    String profilePath   = crewJsonObject.getString("profile_path");

                    crewArray[i] = new Crew(crewElementId, department, gender, crewId, job, name, profilePath);
                }

                // Parse top level id in csv file (never empty)
                int overall_id = Integer.parseInt(csvRecord.get("id"));

                credits.add(castArray, crewArray, overall_id);

                currentNumber++;
                record_count++;
                updateUI(file, record_count);
                
            } //for each csv record
        }
        catch (IOException e){
            System.err.println("unable to open credits file for parsing in.");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        catch (JSONException e){
            System.err.println("[" + record_count + "] --CREDITS-- Unable to read json. Key not found or cannot convert to correct type.");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }


        if (true) return;
        Path filePath = Paths.get(file.getFileName());
        int counter = 0;
        try (BufferedReader fileBufferedReader = Files.newBufferedReader(filePath)) {
            String line = fileBufferedReader.readLine(); //read past header
            // Read each line
            while ((line = fileBufferedReader.readLine()) != null) {
                String[] data = parseCSV(line);

                if (data.length != 3) {
                    System.err.println("[" + counter + "] --CREDITS-- Incorrect number of fields. No of fields found: " + data.length);
                    for (int i = 0; i < data.length; i++) {
                        System.out.println("\ti: " + i + "\t Data: " + data[i]);
                    }

                    currentNumber++;
                    counter++;
                    updateUI(file, counter);
                    continue;
                }
                
                String[][] jsonCast = parseJSON(data[0]);
                Cast[] cast;
                if (jsonCast == null) {
                    cast = new Cast[0];
                } else {
                    cast = new Cast[jsonCast.length];
                }
                for (int i = 0; i < cast.length; i++) {
                    String[] fields = jsonCast[i];
                    
                    int castElementID = -1;
                    String castCharacter = null;
                    String castCreditID = null;
                    int castGender = -1;
                    int castId = -1;
                    String castName = null;
                    int castOrder = -1;
                    String castProfilePath = null;

                    for (int j = 0; j < fields.length; j++) {
                        if (fields[j].indexOf(":") == -1) {
                            System.err.println("[" + counter + "] --CREDITS/CAST-- Unknown string: " + fields[j]);
                            continue;
                        }
                        String field = fields[j].substring(0, fields[j].indexOf(":")).trim();
                        String value = fields[j].substring(fields[j].indexOf(":") + 1).trim();
                        switch (field) {
                            case "\'cast_id\'": 
                                castElementID = Integer.parseInt(value);
                                break;
                            case "\'character\'": 
                                castCharacter = value;
                                break;
                            case "\'credit_id\'": 
                                castCreditID = value;
                                break;
                            case "\'gender\'":
                                castGender = Integer.parseInt(value);
                                break;
                            case "\'name\'":
                                castName = value.replace("\'", "");
                                break;
                            case "\'id\'":
                                 castId = Integer.parseInt(value);
                                 break;
                            case "\'order\'":
                                castOrder = Integer.parseInt(value);
                                break;
                            case "\'profile_path\'":
                                castProfilePath = value;
                                break;
                            default: 
                                System.err.println("[" + counter + "] --CREDITS/CAST-- Unknown field. Field: " + field + "\t Value: " + value);
                        }
                    }

                    cast[i] = new Cast(castElementID, castCharacter, castCreditID, castGender, castId, castName, castOrder, castProfilePath);
                }

                String[][] jsonCrew = parseJSON(data[1]);
                Crew[] crew;
                if (jsonCrew == null) {
                    crew = new Crew[0];
                } else {
                    crew = new Crew[jsonCrew.length];
                }

                for (int i = 0; i < crew.length; i++) {
                    String[] fields = jsonCrew[i];

                    String crewElementID = null;
                    String crewDepartment = null;
                    int crewGender = -1;
                    int crewId = -1;
                    String crewJob = null;
                    String crewName = null;
                    String crewProfilePath = null;

                    for (int j = 0; j < fields.length; j++) {
                        if (fields[j].indexOf(":") == -1) {
                            System.err.println("[" + counter + "] --CREDITS/CREW-- Unknown string: " + fields[j]);
                            continue;
                        }
                        String field = fields[j].substring(0, fields[j].indexOf(":")).trim();
                        String value = fields[j].substring(fields[j].indexOf(":") + 1).trim();
                        switch (field) {
                            case "\'credit_id\'":
                                crewElementID = value;
                                break;
                            case "\'department\'":
                                crewDepartment = value;
                                break;
                            case "\'gender\'":
                                crewGender = Integer.parseInt(value);
                                break;
                            case "\'job\'": 
                                crewJob = value;
                                break;
                            case "\'name\'": 
                                crewName = value.replace("\'", "");
                                break;
                            case "\'id\'":
                                crewId = Integer.parseInt(value);
                                break;
                            case "\'profile_path\'": 
                                crewProfilePath = value;
                                break;
                            default:
                                System.err.println("[" + counter + "] --CREDITS/CREW-- Unknown field. Field: " + field
                                        + "\t Value: " + value);
                        }
                    }
                    crew[i] = new Crew(crewElementID, crewDepartment, crewGender, crewId, crewJob, crewName, crewProfilePath);
                }

                credits.add(cast, crew, Integer.parseInt(data[2]));

                currentNumber++;
                counter++;
                updateUI(file, counter);
            }
        } catch(Exception e) {
            System.err.println("[" + counter + "] --CREDITS-- Unknown error");
            e.printStackTrace();
        }

    }

    private void loadKeywords(InputFile file) {
        System.out.println("\nLoading keywords...");
        Path filePath = Paths.get(file.getFileName());
        File keywordCsvFile = new File(file.getFileName());
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                            .setHeader()
                            .setSkipHeaderRecord(true)
                            .build();
        int record_count = 0;
        try (CSVParser parser = CSVParser.parse(keywordCsvFile, Charset.forName("UTF-8") , csvFormat)){
            for (CSVRecord csvRecord : parser){ // For every csv line, excluding the header
                if (csvRecord.size() != 2){
                    System.err.println("[" + record_count + "] --KEYWORDS-- Incorrect number of fields. Number of fields found: " + csvRecord.size());
                    currentNumber++;
                    record_count++;
                    updateUI(file, record_count);
                    continue;
                }

                int id = Integer.parseInt(csvRecord.get("id"));
                JSONArray jsonKeywordArray = new JSONArray(csvRecord.get("keywords"));
                
                Keyword[] keywordArray = new Keyword[jsonKeywordArray.length()];
                //Read from the json keyword array that looks like "[{'id':100, 'name':'based on the novel'},...]"
                for (int i = 0; i < jsonKeywordArray.length(); i++){
                    JSONObject jsonKeyword = jsonKeywordArray.getJSONObject(i); // each {'id':100, 'name':'based on the novel'} in the array

                    int keyword_id = jsonKeyword.getInt("id");
                    String keyword_name = jsonKeyword.getString("name");
                    
                    keywordArray[i] = new Keyword(keyword_id, keyword_name);
                }
                keywords.add(id, keywordArray);

                currentNumber++;
                record_count++;
                updateUI(file, record_count);
            }
        }
        catch (IOException e){
            System.err.println("unable to open keyword file for parsing in.");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        catch (IllegalArgumentException e){
            System.err.println("[" + record_count + "] --KEYWORDS-- Unable to read csv. Item for specified header not found.");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        catch (JSONException e){
            System.err.println("[" + record_count + "] --KEYWORDS-- Unable to read json. Key not found or cannot convert to correct type.");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }

        if (true) return;
    }

    private void loadMetadata(InputFile file) {
        System.out.println("\nLoading metadata...");
        File metadataCsvFile = new File(file.getFileName());
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                            .setHeader()
                            .setSkipHeaderRecord(true)
                            .build();
        int record_count = 0;
        try (CSVParser parser = CSVParser.parse(metadataCsvFile, Charset.forName("UTF-8"), csvFormat)){
            for (CSVRecord csvRecord : parser){ // For each record line in the file
                if (csvRecord.size() != 24) {
                    System.err.println("[" + record_count + "] --METADATA-- Incorrect number of fields. Number of fields found: " + csvRecord.size());
                }

                // Parse Genres
                JSONArray jsonGenreArray = new JSONArray(csvRecord.get("genres"));
                Genre[] genreArray = new Genre[jsonGenreArray.length()];
                for (int i = 0; i < jsonGenreArray.length(); i++){
                    JSONObject gObject = jsonGenreArray.getJSONObject(i);
                    int genreId = gObject.getInt("id");
                    String genreName = gObject.getString("name");
                    genreArray[i] = new Genre(genreId, genreName);
                }

                // Parse Languages
                JSONArray jsonLanguageArray = new JSONArray(csvRecord.get("spoken_languages"));
                String[] languageArray = new String[jsonLanguageArray.length()];
                for (int i = 0; i < jsonLanguageArray.length(); i++){
                    JSONObject lObject = jsonLanguageArray.getJSONObject(i);
                    String lang_short = lObject.getString("iso_639_1");
                    languageArray[i] = lang_short;
                }

                // Parse Release Date
                String release_in_file = csvRecord.get("release_date");
                Calendar release;
                if (!release_in_file.equals("")){
                    LocalDate date = LocalDate.parse(csvRecord.get("release_date"));
                    release = Calendar.getInstance(); 
                    release.set(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
                }
                else{
                    release = null;
                }

                // Parse Budget
                long budget    = Long.parseLong(csvRecord.get("budget"));
                long revenue   = Long.parseLong(csvRecord.get("revenue"));
                String runtime_in_file = csvRecord.get("runtime");
                double runtime;
                if (runtime_in_file.equals("")){ runtime = -1; }
                else{ runtime = Double.parseDouble(csvRecord.get("runtime")); }
                boolean adult  = Boolean.parseBoolean(csvRecord.get("adult"));
                boolean video  = Boolean.parseBoolean(csvRecord.get("video"));
                int id = Integer.parseInt(csvRecord.get("id"));

                String title            = csvRecord.get("title");
                String originalTitle    = csvRecord.get("original_title");
                String overview         = csvRecord.get("overview");
                String tagline          = csvRecord.get("tagline");
                String status           = csvRecord.get("status"); 
                String originalLanguage = csvRecord.get("original_language");
                String homepage         = csvRecord.get("homepage");
                String poster_path      = csvRecord.get("poster_path");


                boolean movieAdded = movies.add(id, title, originalTitle, overview, tagline, status, 
                                                genreArray, release, budget, revenue, languageArray, 
                                                originalLanguage, runtime, homepage, adult, video, poster_path);
                if (movieAdded){
                    double vote_average = Double.parseDouble(csvRecord.get("vote_average"));
                    int vote_count = Integer.parseInt(csvRecord.get("vote_count"));
                    movies.setVote(id, vote_average, vote_count);

                    String imdbId = csvRecord.get("imdb_id");
                    movies.setIMDB(id, imdbId);

                    Double popularity = Double.parseDouble(csvRecord.get("popularity"));
                    movies.setPopularity(id, popularity);


                    // Add Collection
                    String collectionString = csvRecord.get("belongs_to_collection");
                    int collectionId = -1;
                    String collectionName = null;
                    String collectionPoster = null;
                    String collectionBackdrop = null;
                    if (!collectionString.equals("")){
                        JSONObject collectionObject = new JSONObject(csvRecord.get("belongs_to_collection"));
                        collectionId       = collectionObject.getInt("id");
                        collectionName     = collectionObject.getString("name");
                        collectionPoster   = collectionObject.getString("poster_path");
                        collectionBackdrop = collectionObject.getString("backdrop_path");
                        movies.addToCollection(id, collectionId, collectionName, collectionPoster, collectionBackdrop);
                    }

                    // Add Companies
                    JSONArray jsonCompanyArray = new JSONArray(csvRecord.get("production_companies"));
                    for (int i = 0; i < jsonCompanyArray.length(); i++){
                        JSONObject jsonCompanyObject = jsonCompanyArray.getJSONObject(i);
                        String companyName = jsonCompanyObject.getString("name");
                        int companyId = jsonCompanyObject.getInt("id");
                        movies.addProductionCompany(id, new Company(companyId, companyName));
                    }

                    // Add Countries
                    JSONArray jsonCountryArray = new JSONArray(csvRecord.get("production_countries"));
                    for (int i = 0; i < jsonCountryArray.length(); i++){
                        JSONObject jsonCountryObject = jsonCountryArray.getJSONObject(i);
                        String countryIdShort = jsonCountryObject.getString("iso_3166_1");
                        movies.addProductionCountry(id, countryIdShort);
                    }
                }
                
                currentNumber++;
                record_count++;
                updateUI(file, record_count);

            } //for each record


        }
        catch (IOException e){
            System.err.println("unable to open metadata file for parsing in.");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        if (true) return;

        Path filePath = Paths.get(file.getFileName());
        int counter = 0;
        try (BufferedReader fileBufferedReader = Files.newBufferedReader(filePath)) {
            String line = fileBufferedReader.readLine();
            while ((line = fileBufferedReader.readLine()) != null) {
                String[] data = parseCSV(line);

                if (data.length != 24) {
                    System.err.println("[" + counter + "] --METADATA-- Incorrect number of fields. Number of fields found: " + data.length);
                }

                String[][] jsonGenre = parseJSON(data[3]);
                Genre[] genres;
                if (jsonGenre != null) {
                    genres = new Genre[jsonGenre.length];
                    for (int i = 0; i < genres.length; i++) {
                        int genreID = -1;
                        String genreName = null;
                        String[] fields = jsonGenre[i];
                        for (int j = 0; j < fields.length; j++) {
                            if (fields[j].contains("\'id\': ")) {
                                genreID = Integer.parseInt(fields[j].substring(fields[j].indexOf(":") + 1).trim());
                            } else if (fields[j].contains("\'name\': ")) {
                                genreName = fields[j].substring(fields[j].indexOf(":") + 1).trim();
                            } else {
                                System.err.println("[" + counter + "] --METADATA/GENRE-- Found this, not sure what it is... --> "
                                        + fields[j]);
                            }
                        }

                        if (genreID != -1 && genreName != null) {
                            genres[i] = new Genre(genreID, genreName.replace("\'", ""));
                        } else {
                            System.err.println("[" + counter + "] --METADATA/GENRE-- Incomplete genre found. ID: " + genreID + "\t Name: " + genreName);
                        }
                    }
                } else {
                    genres = new Genre[0];
                }

                String[][] jsonLanguages = parseJSON(data[17]);
                String[] languages;
                if (jsonLanguages != null){
                    languages = new String[jsonLanguages.length];
                    for (int i = 0; i < languages.length; i++) {
                        String[] fields = jsonLanguages[i];
                        for (int j = 0; j < fields.length; j++) {
                            if (fields[j].contains("\'iso_639_1\': ")) {
                                languages[i] = fields[j].substring(fields[j].indexOf(":") + 1).trim().replace("\'", "");
                                break;
                            } else {
                                System.err.println("[" + counter + "] --METADATA/LANGUAGE-- Found this, not sure what it is... --> " + fields[j] + "\t j: " + j);
                            }
                        }
                    }
                } else {
                    languages = new String[0];
                }

                Calendar release = Calendar.getInstance();
                if (data[14] != "") {
                    release.set(Integer.parseInt(data[14].substring(0,4)), 
                            Integer.parseInt(data[14].substring(5,7)),
                            Integer.parseInt(data[14].substring(8)));
                } else {
                    release = null;
                }

                long budget = 0;
                if (data[2] != "") {budget = Long.parseLong(data[2]);}
                long revenue = 0;
                if (data[15] != "") {revenue = Long.parseLong(data[15]);}
                double runtime = 0.0;
                if (data[16] != "") {runtime = Double.parseDouble(data[16]);}
                boolean adult = false;
                if (data[0] != "") {adult = Boolean.parseBoolean(data[0]);}
                boolean video = false;
                if (data[21] != "") {video = Boolean.parseBoolean(data[21]);}

                int id = Integer.parseInt(data[5]);

                boolean movieAdded = movies.add(id, data[20], data[8], data[9], data[19], data[18], genres, release, budget, revenue, languages, data[7], runtime, data[4], adult, video, data[11]);

                if(movieAdded) {
                    movies.setVote(id, Double.parseDouble(data[22]), Integer.parseInt(data[23]));
                    movies.setIMDB(id, data[6]);
                    movies.setPopularity(id, Double.parseDouble(data[10]));

                    if (data[1] != ""){
                        String[][] jsonCollection = parseJSON(data[1]);
                        for (int i = 0; i < jsonCollection.length; i++){
                            String[] fields = jsonCollection[i];

                            int collectionID = -1;
                            String collectionName = null;
                            String collectionPoster = null;
                            String collectionBackdrop = null;

                            for (int j = 0; j < fields.length; j++) {
                                if (fields[j].indexOf(":") == -1) {
                                    System.err.println("[" + counter + "] --METADATA/COLLECTION-- Unknown string: " + fields[j]);
                                    continue;
                                }
                                String field = fields[j].substring(0, fields[j].indexOf(":")).trim();
                                String value = fields[j].substring(fields[j].indexOf(":") + 1).trim();
                                switch (field) {
                                    case "\'id\'":
                                        collectionID = Integer.parseInt(value);
                                        break;
                                    case "\'name\'":
                                        collectionName = value.replace("\'", "");
                                        break;
                                    case "\'poster_path\'":
                                        collectionPoster = value;
                                        break;
                                    case "\'backdrop_path\'":
                                        collectionBackdrop = value;
                                        break;
                                    default:
                                        System.err.println("[" + counter + "] --METADATA/COLLECTION-- Unknown field. Field: " + field + "\t Value: " + value);
                                }
                            }

                            if (collectionID != -1 && collectionName != null) {
                                movies.addToCollection(id, collectionID, collectionName, collectionPoster, collectionBackdrop);
                            } else {
                                System.err.println("[" + counter + "] --METADATA/COLLECTION-- Unknown collection. Collection ID: " + collectionID + "\t Collection Name: " + collectionName + "\t Collection Poster Path: " + collectionPoster + "\t Collection Backdrop Path: " + collectionBackdrop);
                            }
                        }
                    }
                    
                    String[][] jsonCompanies = parseJSON(data[12]);
                    if (data[12] != "" && jsonCompanies != null) {
                        for (int i = 0; i < jsonCompanies.length; i++) {
                            String[] fields = jsonCompanies[i];

                            int companyID = -1;
                            String companyName = null;

                            for (int j = 0; j < fields.length; j++) {
                                if (fields[j].indexOf(":") == -1) {
                                    System.err.println("[" + counter + "] --METADATA/COMPANY-- Unknown string: " + fields[j]);
                                    continue;
                                }
                                String field = fields[j].substring(0, fields[j].indexOf(":")).trim();
                                String value = fields[j].substring(fields[j].indexOf(":") + 1).trim();
                                switch (field) {
                                    case "\'id\'":
                                        companyID = Integer.parseInt(value);
                                        break;
                                    case "\'name\'":
                                        companyName = value.replace("\'", "");
                                        break;
                                    default:
                                        System.err.println("[" + counter + "] --METADATA/COMPANY-- Unknown field. Field: " + field + "\t Value: " + value);
                                }
                            }

                            if (companyID != -1 && companyName != null) {
                                movies.addProductionCompany(id, new Company(companyID, companyName));
                            } else {
                                System.err.println("["+counter+"] --METADATA/COMPANY-- Unknown company. Company ID: " + companyID + "\t Company Name: " + companyName);
                            }
                        }
                    }

                    String[][] jsonCountries = parseJSON(data[13]);
                    if (data[13] != "" && jsonCountries != null) {
                        for (int i = 0; i < jsonCountries.length; i++) {
                            String[] fields = jsonCountries[i];

                            String countryID = null;

                            for (int j = 0; j < fields.length; j++) {
                                if (fields[j].indexOf(":") == -1) {
                                    System.err.println("[" + counter + "] --METADATA/COUNTRY-- Unknown string: " + fields[j]);
                                    continue;
                                }
                                String field = fields[j].substring(0, fields[j].indexOf(":")).trim();
                                String value = fields[j].substring(fields[j].indexOf(":") + 1).trim();
                                switch (field) {
                                    case "\'iso_3166_1\'":
                                        countryID = value.replace("\'", "");
                                        break;
                                    // case "\'name\'":
                                    //     countryName = value;
                                    //     break;
                                    default:
                                        System.err.println("[" + counter + "] --METADATA/COUNTRY-- Unknown field. Field: " + field + "\t Value: " + value);
                                }
                                if(countryID != null) {
                                    break;
                                }
                            }

                            if (countryID != null) {
                                movies.addProductionCountry(id, countryID);
                            } else {
                                System.err.println("[" + counter + "] --METADATA/COUNTRY-- Unknown country. Country ID: " + countryID);
                            }
                        }
                    }
                }

                currentNumber++;
                counter++;
                updateUI(file, counter);
            }
        } catch (Exception e) {
            System.err.println("[" + counter + "] --METADATA -- Unknown error");
            e.printStackTrace();
        }
    }

    private void loadRatings(InputFile file) {
        System.out.println("\nLoading ratings...");
        File ratingsCsvFile = new File(file.getFileName());
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                            .setHeader()
                            .setSkipHeaderRecord(true)
                            .build();
        int record_count = 0;
        try (CSVParser parser = CSVParser.parse(ratingsCsvFile, Charset.forName("utf-8"), csvFormat)){
            for (CSVRecord csvRecord : parser){
                //For each record in csv file
                if (csvRecord.size() != 4){
                    System.err.println("[" + record_count + "] --RATINGS-- Incorrect list of ratings... No. fields found = " + csvRecord.size());
                    currentNumber++;
                    record_count++;
                    updateUI(file, record_count);
                    continue;
                }

                int userId   = Integer.parseInt(csvRecord.get("userId"));
                int movieId  = Integer.parseInt(csvRecord.get("movieId"));
                float rating = Float.parseFloat(csvRecord.get("rating"));

                long ts_in_file = Long.parseLong(csvRecord.get("timestamp"));
                LocalDateTime time = LocalDateTime.ofEpochSecond(ts_in_file, 0 , ZoneOffset.UTC);
                Instant instant = time.atZone(ZoneOffset.UTC).toInstant();
                Date date = Date.from(instant);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                ratings.add(userId, movieId, rating, cal);

                currentNumber++;
                record_count++;
                updateUI(file, record_count);
            }

        }
        catch (IOException e){
            System.err.println("unable to open ratings file for parsing in.");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }


        if (true) return;



        Path filePath = Paths.get(file.getFileName());
        try (BufferedReader fileBufferedReader = Files.newBufferedReader(filePath)) {
            String line = fileBufferedReader.readLine();
            int counter = 0;
            while ((line = fileBufferedReader.readLine()) != null) {
                String[] data = parseCSV(line);
                if (data.length != 4) {
                    System.err.println("[" + counter + "] --RATINGS-- Incorrect list of ratings... No. fields found = " + data.length);
                    currentNumber++;
                    counter++;
                    updateUI(file, counter);
                    continue;
                } else {
                    Calendar timestamp = Calendar.getInstance();
                    timestamp.setTimeInMillis(Long.parseLong(data[3].trim()));
                    ratings.add(Integer.parseInt(data[0].trim()), Integer.parseInt(data[1].trim()), Float.parseFloat(data[2].trim()), timestamp);
                }
                currentNumber++;
                counter++;
                updateUI(file, counter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI(InputFile file, int current) {
        if (loadingBar == null || loadingText == null) {
            return;
        }
        loadingBar.setValue((int) (getPercentage()*(double)loadingBar.getMaximum()));
        loadingString = "Loading ";
        switch (file.getFileType()) {
            case CREDITS: loadingString += "Credits..."; 
                break;
            case KEYWORDS: loadingString += "Keywords..."; 
                break;
            case METADATA: loadingString += "Film Metadata..."; 
                break;
            case RATINGS: loadingString += "Ratings..."; 
                break;
        }

        loadingString += " (" + current + "/" + file.getTotal() + ")";
        loadingText.setText(loadingString);
    }

    private String[] parseCSV(String orgString) {
        int index = 0;
        String[] result = {};
        while (index != -1 && index < orgString.length() ) {
            //Set up some key values
            int endIndex = orgString.indexOf(",", index);
            String csvValue = "";
            boolean flag = false;

            //If there are no more values after this one, flag to break and get all remaining characters
            if (endIndex == -1 || endIndex >= orgString.length()) {
                csvValue = orgString.substring(index);
                flag = true;

            //if it is in "", then find the next one and make necessary adjustments
            } else if (orgString.charAt(index) == '"' && orgString.charAt(index+1) == '"') {
                continue;
            } else if (orgString.charAt(index) == '"') {
                index++;
                endIndex = orgString.indexOf('"', (index+1));
                while ((endIndex + 1) < orgString.length() && orgString.charAt(endIndex+1) == '"') {
                    endIndex = orgString.indexOf('"', (endIndex+2));
                }
                csvValue = orgString.substring(index, endIndex);
                endIndex++;

            //otherwise, just get the requires substring
            } else {
                csvValue = orgString.substring(index, endIndex);
            }

            //Copy over string to a larger array
            String[] tmp = new String[result.length+1];

            int i;
            for (i = 0; i < result.length; i++) {
                tmp[i] = result[i];
            }
            tmp[i] = csvValue;
            result = tmp;

            //If we have flagged there are no more elements, break
            if (flag) {
                break;
            } else {
                index = endIndex+1;
            }
        }

        return result;
    }

    private String[][] parseJSON(String orgString) {
        if (orgString.equals("[]")) {
            return null;
        }

        String[][] result = {};

        //Get start and end points
        int indexSquareBracketOpen = orgString.indexOf("[");
        int indexSquareBracketClose = orgString.lastIndexOf("]");
        String[] roughSplit;

        // For each { } in [ ]
        if (indexSquareBracketOpen != -1 && indexSquareBracketClose != -1) {
            roughSplit = orgString.substring(indexSquareBracketOpen+2, indexSquareBracketClose-1).split("\\}, \\{");
        } else if (indexSquareBracketOpen != -1) {
            roughSplit = orgString.substring(indexSquareBracketOpen + 2).split("\\}, \\{");
        } else if (indexSquareBracketClose != -1) {
            roughSplit = orgString.substring(1, indexSquareBracketClose - 1).split("\\}, \\{");
        } else {
            roughSplit = orgString.substring(1, orgString.length()).split("\\}, \\{");
        }
        
        result = new String[roughSplit.length][0];
        for (int i = 0; i < roughSplit.length; i++) {
            // innerSplit = Split on , not in "" ""
            String[] roughInnerSplit = roughSplit[i].split(", \'");
            for (int j = 1; j < roughInnerSplit.length; j++) {
                roughInnerSplit[j] = "\'" + roughInnerSplit[j];
            }

            // Set result[i] = innerSplit
            result[i] = roughInnerSplit;
        }

        return result;
    }
}


