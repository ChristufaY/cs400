import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The main class for the application which contains its main method and subclasses javafx.application.Application class.
 */
public class App extends Application {

    // the skip list that contains the data set
    protected SkipList<Date,SquirrelSighting> dataSkipList = new SkipList<>();
    // date and time format to render Date objects into strings
    protected static DateFormat dateFormat = new SimpleDateFormat("MM/dd yyyy a");

    // the javafx chart we use to display the data set
    protected BarChart<String,Number> sightingsChart = makeChart();
    // the minimum date selected by the user
    protected Date minSelectedDate = null;
    // the maximum date selected by the user
    protected Date maxSelectedDate = null;
    // the data attribute selected by the user
    protected String selectedAttribute = "none";

    /**
     * JavaFX start method that sets up our UI.
     * @param the JavaFX stage (a representation of our window)
     */
    @Override
    public void start(final Stage stage) {

        // set the window title
        stage.setTitle("Squirrel Sighting Explorer");

        // create the menu bar with File and Exit menu items
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItemOpen = new MenuItem("Open");
        MenuItem menuItemExit = new MenuItem("Exit");

        // create a border pane as the main layout manager
        // for the contents of our window; the menu bar goes
        // on the top
        BorderPane mainPane = new BorderPane();
        menuFile.getItems().addAll(menuItemOpen, menuItemExit);
        menuBar.getMenus().addAll(menuFile);
        mainPane.setTop(menuBar);

        // connect event handler to the menu iterms
        menuItemExit.setOnAction((event) -> Platform.exit());
        menuItemOpen.setOnAction((event) -> {
            // show the JavaFX file selector window
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open CSV File");
            File csvFile = fileChooser.showOpenDialog(stage);
            // once we have chosen a file, load it into the skip list,
            // display it in the bar chart and create a new selector pane
            // for it and add it to the bottom of the boarder layout
            try {
                loadFile(csvFile);
                mainPane.setCenter(sightingsChart);
                mainPane.setBottom(makeDataSelectorPane());
            } catch(IOException|NullPointerException e) {
                System.out.println("Error while reading file: " + csvFile);
                e.printStackTrace();
            }
        });

        // create a new scene to put into our window and add our mein
        // border pane to it
        Scene scene = new Scene(mainPane,800,600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method creates a data selector pane, which contains the range selecctor
     * for dates as well as the attribute selector for additional data series in
     * the bar chart.
     * @return
     */
    protected Pane makeDataSelectorPane() {
        // the base layout of the selector pane is a VBox
        VBox dataSelectorPane = new VBox();

        // we use an anonymous class that can convert the double value we get from the
        // sliders into a date and further to a string (for tick marks of sliders)
        StringConverter<Double> sliderLabelConverter = new StringConverter<Double>() {

            private long earliestDate = dataSkipList.getSmallestKey().getTime();
            private long latestDate = dataSkipList.getLargestKey().getTime();
            private long range = latestDate - earliestDate;
            private Date forFormatting = new Date();

            @Override
            public String toString(Double n) {
                long timeStamp = earliestDate + (long)((double)range*n);
                forFormatting.setTime(timeStamp);
                return dateFormat.format(forFormatting);
            }

            @Override
            public Double fromString(String s) {
                long dateInMilliseconds = 0;
                try {
                    dateInMilliseconds = dateFormat.parse(s).getTime();
                } catch(ParseException e) {
                    System.out.println("Cannot parse string " + s);
                    e.printStackTrace();
                }
                return ((double)dateInMilliseconds-earliestDate)/(double)range;
            }
        };

        // combine the labels and slider for the min date selection line
        // using a border pane as the base layout
        BorderPane minDateSelectorBox = new BorderPane();
        StackPane minLabelPane = new StackPane();
        minLabelPane.setPrefWidth(150);
        minLabelPane.getChildren().addAll(new Label("Start Date: "));
        minDateSelectorBox.setLeft(minLabelPane);
        Slider minDateSelector = new Slider(0.0, 1.0, 0.0);
        minDateSelector.setShowTickMarks(true);
        minDateSelector.setShowTickLabels(true);
        minDateSelector.setMajorTickUnit(0.25f);
        minDateSelector.setBlockIncrement(0.1f);
        // we use the label converted from above to generate the tick marks for the slider
        minDateSelector.setLabelFormatter(sliderLabelConverter);
        Label minSelectedDate = new Label("");
        StackPane minSelectedDatePane = new StackPane();
        minSelectedDatePane.setPrefWidth(200);
        minSelectedDatePane.getChildren().addAll(minSelectedDate);
        minDateSelectorBox.setCenter(minDateSelector);
        minDateSelectorBox.setRight(minSelectedDatePane);
        // handle the event that is generated when the min slider value changes
        // the value we get is between 0 and 1, and we convert it to a date between
        // the start and the end date from the data set
        minDateSelector.valueProperty().addListener((obValue, oldValue, newValue) -> {
            String stringDate = sliderLabelConverter.toString(newValue.doubleValue());
            minSelectedDate.setText(stringDate);
            try {
                this.minSelectedDate = dateFormat.parse(stringDate);
            } catch (ParseException e) {
                System.out.println("Cannot parse string " + e);
                e.printStackTrace();
            }
            updateChart();
        });

        // combine the labels and slider for the max date selection line
        // using a border pane as the base layout
        BorderPane maxDateSelectorBox = new BorderPane();
        StackPane maxLabelPane = new StackPane();
        maxLabelPane.setPrefWidth(150);
        maxLabelPane.getChildren().addAll(new Label("End Date: "));
        maxDateSelectorBox.setLeft(maxLabelPane);
        Slider maxDateSelector = new Slider(0.0, 1.0, 0.0);
        maxDateSelector.setShowTickMarks(true);
        maxDateSelector.setShowTickLabels(true);
        maxDateSelector.setMajorTickUnit(0.25f);
        maxDateSelector.setBlockIncrement(0.1f);
        // we use the label converted from above to generate the tick marks for the slider
        maxDateSelector.setLabelFormatter(sliderLabelConverter);
        Label maxSelectedDate = new Label("");
        StackPane maxSelectedDatePane = new StackPane();
        maxSelectedDatePane.setPrefWidth(200);
        maxSelectedDatePane.getChildren().addAll(maxSelectedDate);
        maxDateSelectorBox.setCenter(maxDateSelector);
        maxDateSelectorBox.setRight(maxSelectedDatePane);
        // handle the event that is generated when the max slider value changes
        // the value we get is between 0 and 1, and we convert it to a date between
        // the start and the end date from the data set
        maxDateSelector.valueProperty().addListener((obValue, oldValue, newValue) -> {
            String stringDate = sliderLabelConverter.toString(newValue.doubleValue());
            maxSelectedDate.setText(stringDate);
            try {
                this.maxSelectedDate = dateFormat.parse(stringDate);
            } catch (ParseException e) {
                System.out.println("Cannot parse string " + e);
                e.printStackTrace();
            }
            updateChart();
        });

        // create a "toggle group" that combines all radio buttons
        // and lets us get events when one of them is selected
        ToggleGroup radioButtonGroup = new ToggleGroup();
        RadioButton noneRadioButton = new RadioButton("none");
        noneRadioButton.setToggleGroup(radioButtonGroup);
        noneRadioButton.setSelected(true);

        RadioButton ageRadioButton = new RadioButton("age");
        ageRadioButton.setToggleGroup(radioButtonGroup);
        
        RadioButton runningRadioButton = new RadioButton("Running");
        runningRadioButton.setToggleGroup(radioButtonGroup);
        
        RadioButton chasingRadioButton = new RadioButton("Chasing");
        chasingRadioButton.setToggleGroup(radioButtonGroup);
        
        RadioButton climbingRadioButton = new RadioButton("Climbing");
        climbingRadioButton.setToggleGroup(radioButtonGroup);
        
        RadioButton eatingRadioButton = new RadioButton("Eating");
        eatingRadioButton.setToggleGroup(radioButtonGroup);
        
        RadioButton foragingRadioButton = new RadioButton("Foraging");
        foragingRadioButton.setToggleGroup(radioButtonGroup);
        // TODO: Add more selections to explore the data and
        //       organize them in a GridPane.

        Group bGroup = new Group();
        bGroup.getChildren().addAll(noneRadioButton, ageRadioButton, runningRadioButton, chasingRadioButton, climbingRadioButton, eatingRadioButton,
        		foragingRadioButton);
        noneRadioButton.setLayoutX(-120);
        noneRadioButton.setLayoutY(10);
        
        ageRadioButton.setLayoutX(-60);
        ageRadioButton.setLayoutY(10);
        
        runningRadioButton.setLayoutX(10);
        runningRadioButton.setLayoutY(10);
        
        chasingRadioButton.setLayoutX(150);
        chasingRadioButton.setLayoutY(10);
        
        climbingRadioButton.setLayoutX(-200);
        climbingRadioButton.setLayoutY(10);
        
        eatingRadioButton.setLayoutX(-270);
        eatingRadioButton.setLayoutY(10);
        
        foragingRadioButton.setLayoutX(320);
        foragingRadioButton.setLayoutY(10);

        TitledPane selectAttributePane = new TitledPane("Select attribute to view in chart:", bGroup);
        selectAttributePane.setCollapsible(false);
        // add an event listeners to teh radio group that listens for changes in the selected radio button
        // and updates the charts based on it
        radioButtonGroup.selectedToggleProperty().addListener((obValue, oldValue, newValue) -> {
            this.selectedAttribute = ((RadioButton)newValue).getText();
            updateChart();
        });
        radioButtonGroup.selectedToggleProperty().addListener((obValue, oldValue, newValue) -> {
            this.selectedAttribute = ((RadioButton)newValue).getText();
            updateChart();
        });

        // add the min date selector, max date selector and attribute selector pane to our data selector VBox
        dataSelectorPane.getChildren().addAll(minDateSelectorBox, maxDateSelectorBox, selectAttributePane);

        return dataSelectorPane;
    }

    /**
     * This method update the chart when any of the selectors change. The data needed (selector values and
     * bar chart object) is stored in 
     */
    protected void updateChart() {
        // the animation routine is unfortunately buggy and will cause the chart
        // to not update correctly, so we disable it
        this.sightingsChart.setAnimated(false);

        // create the main data series for the bar chart which will contain
        // all our sighting counts for each time step
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        // clear all old data series from the chart (if there are any)
        this.sightingsChart.getData().clear();
        // add our main data series to the chart
        this.sightingsChart.getData().add(series); 

        // we give the main series the name "All Sightings"
        series.setName("All Sightings");
        // get the lower and higher bounds and get an iterator as the result of a range query to the skip list
        Date min = this.minSelectedDate == null ? this.dataSkipList.getSmallestKey() : this.minSelectedDate;
        Date max = this.maxSelectedDate == null ? this.dataSkipList.getSmallestKey() : this.maxSelectedDate; 
        Iterator<SquirrelSighting> iter = this.dataSkipList.getRange(min, max);

        // create a hash table that we use to store attribute value counts for the selected attribute
        HashMap<String,Integer> attributeValueCount = new HashMap<String,Integer>();
        // create a hash table to store data series for every value for the selected attribute
        HashMap<String,XYChart.Series<String,Number>> attributeValueSeries = new HashMap<String,XYChart.Series<String,Number>>();
        Date currentDate = null;
        int count = 0;
        while (iter.hasNext()) {
            SquirrelSighting currentSighting = iter.next();
            if (currentDate == null)
                currentDate = currentSighting.getDateTime();
            // create new data points when we jump from one date to another
            if (currentDate != null && !currentDate.equals(currentSighting.getDateTime())) {
                // currentDate = currentSighting.getDateTime();
                // create a new data point for the main series
                series.getData().add(new XYChart.Data<>(dateFormat.format(currentDate), count));
                // and for every series for attribute values that we found in the data
                for (String attributeValue : attributeValueCount.keySet()) {
                    if (!attributeValueSeries.containsKey(attributeValue)) {
                        attributeValueSeries.put(attributeValue, new XYChart.Series<String,Number>());
                        attributeValueSeries.get(attributeValue).setName(this.selectedAttribute + ":" + attributeValue);
                        this.sightingsChart.getData().add(attributeValueSeries.get(attributeValue));
                    }
                    XYChart.Series<String,Number> attributeSeries = attributeValueSeries.get(attributeValue);
                    attributeSeries.getData().add(new XYChart.Data<String,Number>(dateFormat.format(currentDate), attributeValueCount.get(attributeValue)));
                }
                // reset both counts for the main series and all other series
                count = 0;
                for (String attributeValue : attributeValueCount.keySet()) {
                    attributeValueCount.put(attributeValue, 0);
                }
                currentDate = currentSighting.getDateTime();
            }
            // increase count for main data series
            count++;
            //  find the selected attribute
            if (this.selectedAttribute != "none" && this.selectedAttribute != null) {
                // add the value to the hash table if not present, then increase the count
                String attributeValue = currentSighting.getAttributeValue(this.selectedAttribute);
                if (!attributeValueCount.containsKey(attributeValue))
                    attributeValueCount.put(attributeValue, 0);
                attributeValueCount.put(attributeValue, attributeValueCount.get(attributeValue) + 1);
            }
        }
        // and once more for the last series
        series.getData().add(new XYChart.Data<>(dateFormat.format(currentDate), count));
        for (String attributeValue : attributeValueCount.keySet()) {
            if (!attributeValueSeries.containsKey(attributeValue)) {
                attributeValueSeries.put(attributeValue, new XYChart.Series<String,Number>());
                attributeValueSeries.get(attributeValue).setName(this.selectedAttribute + ":" + attributeValue);
                this.sightingsChart.getData().add(attributeValueSeries.get(attributeValue));
            }
            XYChart.Series<String,Number> attributeSeries = attributeValueSeries.get(attributeValue);
            attributeSeries.getData().add(new XYChart.Data<String,Number>(dateFormat.format(currentDate), attributeValueCount.get(attributeValue)));
        }
    }

    /**
     * Read in a data file and parse it into the skip list.
     * @param csvFile the source data csv file
     * @throws IOException if there is a problem reading the file
     */
    protected void loadFile(File csvFile) throws IOException {
        if (csvFile == null)
            throw new NullPointerException("csvFile cannot be null");
        System.out.println(csvFile);
        List<String> headLine = null;
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String nextLine = null;
        while ((nextLine = br.readLine()) != null) {
            if (headLine == null) {
                headLine = handleCSVRow(nextLine);
            } else {
                SquirrelSighting sighting = new SquirrelSighting(headLine, handleCSVRow(nextLine));
                this.dataSkipList.put(sighting.getDateTime(), sighting);
            }
        }
        br.close();
    }

    /**
     * This is a private helper method that generates the chart that we use to display the data by creating one
     * numerical (vertical) and one categorical (horizontal) axis.
     * @return the new chart created
     */
    protected BarChart<String,Number> makeChart() {
        Axis<String> xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);

        bc.setTitle("Squirrel Sightings");
        xAxis.setLabel("Date");
        yAxis.setLabel("Number of Sightings");
        
        return bc;
    }

    /**
     * Helper method that parses a single line from a data csv file and returns a list of its
     * columns.
     * @param csvLine the string read from the csv file
     * @return a list of the columns of the file
     * @throws IOException if there is a problem reading the file
     */
    protected static List<String> handleCSVRow(String csvLine) throws IOException {
		String[] array = csvLine.split(",");
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();
		}
		List<String> quotesResolvedList = new ArrayList<String>(array.length);
		boolean inQuotes = false;
		StringBuffer sb = null;
		for (int i = 0; i < array.length; i++) {
			if (array[i].startsWith("\"") && !array[i].endsWith("\"")) {
				inQuotes = true;
				sb = new StringBuffer();
				sb.append(array[i]);
				sb.deleteCharAt(0);
			} else if (array[i].endsWith("\"") && !array[i].startsWith("\"")) {
				if (!inQuotes) {
                    System.out.println(array[i]);
					throw new IOException("Problem reading line: " + csvLine);
				}
				sb.append(",");
				sb.append(array[i]);
				sb.deleteCharAt(sb.length() - 1);
				quotesResolvedList.add(sb.toString());
				sb = null;
				inQuotes = false;
			} else if (inQuotes) {
				sb.append(",");
				sb.append(array[i]);
			} else {
				quotesResolvedList.add(array[i]);
			}
		}
		return quotesResolvedList;
	}

    public static void main(String[] args) {
        Application.launch();
    }

}
