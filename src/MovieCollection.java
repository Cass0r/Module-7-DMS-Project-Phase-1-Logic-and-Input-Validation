//Required for the functions to operate such as Hashmap, scanner and Map.
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;

public class MovieCollection {

    private Map<String,Movie> movies;

    public MovieCollection(){
        movies = new HashMap<>();
    }
//----------------------------------------------------------------------------------------------------------------------
//addMovie(movie: Movie): boolean
    public  boolean addMovie(Movie movie){
        if (movies.containsKey(movie.getTitle())) {
            System.out.println("Error: Movie already exists in the collection.");
            return false; // Movie already exists
        }
        movies.put(movie.getTitle(), movie);
        return true;
    }
    //----------------------------------------------------------------------------------------------------------------------
//removeMovie(title: String) boolean
    //leave message for movie that was removed
    public boolean removeMovie(String title) {
        if (movies.remove(title) == null) {
            System.out.println("Error: Movie not found.");
            return false;
        }
        System.out.println("Movie has been found and removed.");
        return true;
    }
    //----------------------------------------------------------------------------------------------------------------------
//updateMovie(movie :Movie) boolean
    public boolean updateMovie(String title, String field, String newValue) {
        Movie movie = movies.get(title);
        if (movie == null) {
            System.out.println("Error: Movie not found.");
            return false;
        }

        switch (field.toLowerCase()) {
            case "title":
                // If updating title, we must also check if the new title already exists
                if (movies.containsKey(newValue)) {
                    System.out.println("Error: Movie with this title already exists.");
                    return false;
                }
                movie.setTitle(newValue);
                movies.put(newValue, movie);
                movies.remove(title); // Remove old title
                break;
            case "releaseyear":
                int newYear = Integer.parseInt(newValue);
                if (newYear < 1900 || newYear > 2025) {
                    System.out.println("Error: The release year must be between 1900 and 2025.");
                    return false;
                }
                movie.setRelease_Year(newYear);
                break;
            case "genre":
                movie.setGenre(newValue);
                break;
            case "director":
                movie.setDirector(newValue);
                break;
            case "rating":
                float newRating = Float.parseFloat(newValue);
                if (newRating < 0 || newRating > 100) {
                    System.out.println("Error: The rating must be between 0 and 100.");
                    return false;
                }
                movie.setRating(newRating);
                break;
            case "watchedstatus":
                movie.setWatched_Status(Boolean.parseBoolean(newValue));
                break;
            default:
                System.out.println("Error: Invalid field.");
                return false;
        }

        System.out.println("Movie updated successfully.");
        return true;
    }


//----------------------------------------------------------------------------------------------------------------------
//getMovie():List<Movie>
    public void Display_MovieCollection(){
        if (movies.isEmpty()) {
            System.out.println("No movies in the collection.");
        } else {
            for (Movie movie : movies.values()) {
                System.out.println(movie);
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    public float calculateAverageRating() {
        if (movies.isEmpty()) {
            System.out.println("No movies to calculate an average rating.");
            return 0;
        }
        float sum = 0;
        for (Movie movie : movies.values()) {
            sum += movie.getRating();
        }
        float average = sum / movies.size();

        // Format the average to 2 decimal places
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedAverage = df.format(average);

        // Output the formatted average
        System.out.println("Average Movie Rating: " + formattedAverage);
        return Float.parseFloat(formattedAverage);
    }

//----------------------------------------------------------------------------------------------------------------------
//upload data through textfile
    public void addMoviesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format: Title,Year,Genre,Director,Rating,Watched
                if (parts.length == 6) {
                    String title = parts[0].trim();
                    int year = Integer.parseInt(parts[1].trim());
                    String genre = parts[2].trim();
                    String director = parts[3].trim();
                    float rating = Float.parseFloat(parts[4].trim());
                    boolean watched = Boolean.parseBoolean(parts[5].trim());

                    addMovie(new Movie(title, year, genre, director, rating, watched));
                    System.out.println("Added movie: " + title);
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing movie data. Please ensure file format is correct.");
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//Menu
//This method displays a menu that allows the user to interact with the system.
    public void Movie_Menu () {
        Scanner sc = new Scanner(System.in);
        int Option;

        do {
            System.out.println("\nMovie Collection Menu:");
            System.out.println("1. Add Movie");
            System.out.println("2. Remove Movie");
            System.out.println("3. Update Movie Collection");
            System.out.println("4. Display All Movies in Collection");
            System.out.println("5. Calculate Average Movie Rating");
            System.out.println("6. Exit Menu");
            System.out.print("Enter your option by the number associated: ");
            Option = sc.nextInt();
            System.out.print("\n");

//Using the switch for the menu so user can enter the option they choose by using the number associated in  the print
// above.
            switch (Option) {
//----------------------------------------------------------------------------------------------------------------------
                //Adding movie
                case 1:
                    //add the switch option for add movie manually or by text file
                    System.out.println("How would you like to add the Movie");
                    System.out.println("1. Textfile (Please place textfile in (C:) for data retrival.");
                    System.out.println("2. Manually");
                    System.out.print("Enter Option Here:");
                    int option2 = sc.nextInt();


                    switch (option2) {

                        //textfile option
                        case 1:
                            String filePath = "C:\\movies.txt";
                            addMoviesFromFile(filePath);
                            break;

                        //manually
                        case 2:
//keep movie titles under 45 letters or no characters make sure to allow re-entry
                            System.out.print("Enter Movie Title: ");
                            sc.nextLine();
                            String title = sc.nextLine();

                            while (title.length() < 1) {
                                System.out.println("Error: Title entered had no characters entered, please re-enter proper amount.");
                                System.out.print("Enter Movie Title: ");
                                title = sc.nextLine();
                            }

                            while (title.length() > 45) {
                                System.out.println("Error: Title exceeds 45 characters. Please enter a shorter title.");
                                System.out.print("Enter Movie Title: ");
                                title = sc.nextLine();
                            }

//allow movies from 1900 to 2025 make sure to allow re-entry
                            //or if letters are typed in
                            int year = 0;
                            while (true) {
                                System.out.print("Enter release year: ");
                                if (sc.hasNextInt()) {
                                    year = sc.nextInt();
                                    if (year >= 1890 && year <= 2025) {
                                        sc.nextLine(); // Consume leftover newline
                                        break;
                                    } else {
                                        System.out.println("Error: Year must be between 1890 and 2025.");
                                    }
                                } else {
                                    System.out.println("Error: Please enter a valid number.");
                                    sc.next(); // Consume invalid input
                                }
                            }

//only specific genre words in make sure to allow re-entry
                            String[] validGenres = {"Action", "Crime", "Drama", "Fantasy", "Horror", "Comedy", "Romance", "Science Fiction", "Sports", "Thriller", "Mystery", "War", "Western"};

                            // Get the genre input and validate
                            String genre;
                            boolean validGenre = false;
                            do {
                                System.out.print("Enter genre: ");
                                genre = sc.nextLine();

                                // Check if the genre is valid
                                for (String valid : validGenres) {
                                    if (genre.equalsIgnoreCase(valid)) {
                                        validGenre = true;
                                        break;
                                    }
                                }

                                // If the genre is invalid, prompt the user to enter again
                                if (!validGenre) {
                                    System.out.println("Error: Invalid genre. Please enter a valid genre from the list: Action, Crime, Drama, Fantasy, Horror, Comedy, Romance, Science Fiction, Sports, Thriller, Mystery, War, Western ");
                                }
                            } while (!validGenre);

//keep up from 2 to 25 characters make sure to allow re-entry
                            // no special letters for name
                            System.out.print("Enter director: ");
                            String director = sc.nextLine();

// Regular expression: Only allows letters (a-z, A-Z) and spaces
                            while (!director.matches("^[a-zA-Z ]+$") || director.length() < 2 || director.length() > 25) {
                                if (!director.matches("^[a-zA-Z ]+$")) {
                                    System.out.println("Error: Name contains invalid characters. Please enter only letters and spaces.");
                                } else if (director.length() < 2) {
                                    System.out.println("Error: Name entered was below 2 characters, please re-enter name.");
                                } else if (director.length() > 25) {
                                    System.out.println("Error: Name entered was above 25 characters, please re-enter name.");
                                }

                                System.out.print("Enter director: ");
                                director = sc.nextLine();
                            }

//keep rating from 0 to 100 make sure to allow re-entry
                            float rating = -1; // Initialize with an invalid value

                            while (true) {
                                System.out.print("Enter rating: ");
                                String input = sc.nextLine();

                                // Check if the input is a valid floating-point number
                                try {
                                    rating = Float.parseFloat(input);

                                    // Check if it's within the valid range
                                    if (rating >= 0 && rating <= 100) {
                                        break; // Valid input, exit loop
                                    } else {
                                        System.out.println("Error: The rating must be between 0 and 100. Please enter a valid rating.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Invalid input. Please enter a number between 0 and 100.");
                                }
                            }

//Watch status should be true or false
                            boolean watched;
                            while (true) {
                                System.out.print("Watched? (true/false): ");
                                String input = sc.next().trim().toLowerCase();  // Use next() to capture input
                                // Clear the buffer after using next() to avoid leftover characters
                                sc.nextLine();

                                // Check if the input is valid (true or false)
                                if (input.equals("true")) {
                                    watched = true;
                                    break;  // Exit the loop once a valid boolean is entered
                                } else if (input.equals("false")) {
                                    watched = false;
                                    break;  // Exit the loop once a valid boolean is entered
                                } else {
                                    System.out.println("Error: Please enter 'true' or 'false'.");
                                }
                            }

//All attributes added will be added to constructor and onto to the list
                            addMovie(new Movie(title, year, genre, director, rating, watched));
                            break;
                    }
                    break;
//----------------------------------------------------------------------------------------------------------------------
                //Removing movie
                case 2:
                    System.out.print("Enter title to remove: ");
                    sc.nextLine();
                    String remove_title = sc.nextLine();
                    removeMovie(remove_title);
                    break;
//----------------------------------------------------------------------------------------------------------------------
                //Update Movie Collection
                case 3:

                    // Update Movie Collection
                    sc.nextLine(); // Clear the buffer before getting new input
                    // Enter the title of the movie to update
                    System.out.print("Enter title of the movie to update: ");
                    String updateTitle = sc.nextLine().trim();

                    // Check if the movie exists before proceeding
                    if (!movies.containsKey(updateTitle)) {
                        System.out.println("Error: Movie not found.");
                        break;
                    }

                    // Ask for the field to update
                    System.out.print("Enter field to update (title, releaseYear, genre, director, rating, watchedStatus): ");
                    String field = sc.nextLine().trim();

                    // Ask for the new value to update
                    String newValue = "";
                    boolean validUpdate = false;

                    switch (field.toLowerCase()) {
                        case "title":
                            // Keep movie titles under 45 characters
                            while (true) {
                                System.out.print("Enter new title (max 45 characters): ");
                                newValue = sc.nextLine().trim();
                                if (newValue.length() < 1) {
                                    System.out.println("Error: Title entered had no characters entered, please re-enter.");
                                } else if (newValue.length() > 45) {
                                    System.out.println("Error: Title exceeds 45 characters. Please enter a shorter title.");
                                } else {
                                    validUpdate = true;
                                    break;
                                }
                            }
                            break;

                        case "releaseyear":
                            // Allow movies from 1900 to 2025
                            while (true) {
                                System.out.print("Enter new release year (1900-2025): ");
                                try {
                                    int year = Integer.parseInt(sc.nextLine().trim());
                                    if (year < 1900 || year > 2025) {
                                        System.out.println("Error: The year you entered was invalid. Please re-enter a year between 1900 and 2025.");
                                    } else {
                                        newValue = Integer.toString(year);
                                        validUpdate = true;
                                        break;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Invalid year format. Please enter a valid number.");
                                }
                            }
                            break;

                        //use different variables for this case
                        case "genre":
                            // Only specific genre words are allowed
                            String[] validGenress = {"Action", "Crime", "Drama", "Fantasy", "Horror", "Comedy", "Romance", "Science Fiction", "Sports", "Thriller", "Mystery", "War", "Western"};
                            while (true) {
                                System.out.print("Enter new genre: ");
                                newValue = sc.nextLine().trim();
                                boolean validGenree = false;
                                for (String valid : validGenress) {
                                    if (newValue.equalsIgnoreCase(valid)) {
                                        validGenree = true;
                                        break;
                                    }
                                }
                                if (!validGenree) {
                                    System.out.println("Error: Invalid genre. Please enter a valid genre from the list.");
                                } else {
                                    validUpdate = true;
                                    break;
                                }
                            }
                            break;

                        case "director":
                            // Director name should be between 2 and 25 characters
                            while (true) {
                                System.out.print("Enter new director's name (2-25 characters): ");
                                newValue = sc.nextLine().trim();
                                if (newValue.length() < 2) {
                                    System.out.println("Error: Name entered was below 2 characters, please re-enter.");
                                } else if (newValue.length() > 25) {
                                    System.out.println("Error: Name entered was above 25 characters, please re-enter.");
                                } else {
                                    validUpdate = true;
                                    break;
                                }
                            }
                            break;

                        case "rating":
                            // Rating should be between 0 and 100
                            while (true) {
                                System.out.print("Enter new rating (0-100): ");
                                try {
                                    float rating = Float.parseFloat(sc.nextLine().trim());
                                    if (rating < 0 || rating > 100) {
                                        System.out.println("Error: The rating must be between 0 and 100. Please enter a valid rating.");
                                    } else {
                                        newValue = Float.toString(rating);
                                        validUpdate = true;
                                        break;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Invalid rating format. Please enter a valid number.");
                                }
                            }
                            break;

                        case "watchedstatus":
                            // Watched status should be 'true' or 'false'
                            while (true) {
                                System.out.print("Watched? (true/false): ");
                                String input = sc.nextLine().trim().toLowerCase();
                                if (input.equals("true")) {
                                    newValue = "true";
                                    validUpdate = true;
                                    break;
                                } else if (input.equals("false")) {
                                    newValue = "false";
                                    validUpdate = true;
                                    break;
                                } else {
                                    System.out.println("Error: Please enter 'true' or 'false'.");
                                }
                            }
                            break;

                        default:
                            System.out.println("Error: Invalid field selected.");
                            break;
                    }

                    // Once a valid update value is entered, proceed with the update
                    if (validUpdate) {
                        if (updateMovie(updateTitle, field, newValue)) {
                            System.out.println("Movie updated successfully.");
                        } else {
                            System.out.println("Failed to update movie.");
                        }
                    }
                    break;
//----------------------------------------------------------------------------------------------------------------------
                //Display All Movies in Collection
                case 4:
                    Display_MovieCollection();
                    break;
//----------------------------------------------------------------------------------------------------------------------
                //Calculate Average Movie Rating
                case 5:
                    calculateAverageRating();
                    break;
//----------------------------------------------------------------------------------------------------------------------
                //Exiting the menu
                case 6:
                    System.out.println("Exiting Menu...");
                    break;

                //incase there is a miss input of any other numbers then what is given.
                default:
                    System.out.println("Invalid option, please re-enter the option offered above.");
            }
        } while (Option != 6);
    }//class
}
