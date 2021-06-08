package sceneUtils;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import utils.Month;
import java.time.LocalDate;

public class DateBox extends HBox
{
    private ComboBox dayBox;
    private final ComboBox<Month> monthBox;
    private ComboBox yearBox;
    private LocalDate currentDate;

    public DateBox()
    {
        //Check the current date
        currentDate = LocalDate.now();

        //Instantiate combo boxes
        dayBox = new ComboBox<Integer>();
        monthBox = new ComboBox<Month>();
        yearBox = new ComboBox<Integer>();

        //Add items to combo boxes and set starting values
        instantiateMonthCombo();
        instantiateDayCombo();
        instantiateYearCombo();

        //Add combo boxes to container
        this.getChildren().addAll(monthBox, dayBox, yearBox);

        //Add event listener to month combo box
        monthBox.setOnAction(event ->
        {
            updateDayBox();
        });
    }//constructor

    private void instantiateMonthCombo()
    {
        //Add Months to the month combo box
        monthBox.getItems().addAll(Month.JAN, Month.FEB, Month.MAR, Month.APR,
                Month.MAY, Month.JUN, Month.JUL, Month.AUG,
                Month.SEP, Month.OCT, Month.NOV, Month.DEC);
        //Set value to the current local month
        switch(currentDate.getMonth())
        {
            case FEBRUARY:
                monthBox.setValue(Month.FEB);
                break;
            case MARCH:
                monthBox.setValue(Month.MAR);
                break;
            case APRIL:
                monthBox.setValue(Month.APR);
                break;
            case MAY:
                monthBox.setValue(Month.MAY);
                break;
            case JUNE:
                monthBox.setValue(Month.JUN);
                break;
            case JULY:
                monthBox.setValue(Month.JUL);
                break;
            case AUGUST:
                monthBox.setValue(Month.AUG);
                break;
            case SEPTEMBER:
                monthBox.setValue(Month.SEP);
                break;
            case OCTOBER:
                monthBox.setValue(Month.OCT);
                break;
            case NOVEMBER:
                monthBox.setValue(Month.NOV);
                break;
            case DECEMBER:
                monthBox.setValue(Month.DEC);
                break;
            default:
                monthBox.setValue(Month.JAN);
        }//switch
    }//instantiateMonthCombo

    private void instantiateDayCombo()
    {
        //Add days to day combo box
        for(int i = 1; i <= monthBox.getValue().getNumDays(); i++)
            dayBox.getItems().add(i);
        //Set value to current local day
        dayBox.setValue(currentDate.getDayOfMonth());
    }//instantiateDayCombo

    private void instantiateYearCombo()
    {
        int year = currentDate.getYear();
        for(int i = year; i <= (year + 20); i++)
            yearBox.getItems().add(i);
        yearBox.setValue(year);
    }//instantiateYearCombo

    //Updates the day combo box to display the correct number of days associated with
    //the currently selected month
    private void updateDayBox()
    {
        ObservableList dayBoxItems = dayBox.getItems();
        int currentNumItems = dayBoxItems.size();
        int numDaysInCurrentMonth = monthBox.getValue().getNumDays();
        if(currentNumItems == numDaysInCurrentMonth)
            return;
        else if(currentNumItems < numDaysInCurrentMonth)
        {
            dayBox.getItems().add(currentNumItems + 1);
            updateDayBox();
        }
        else
        {
            dayBox.getItems().remove(currentNumItems - 1);
            updateDayBox();
        }
    }//updateDayBox

    public int getDay()
    {
        return (int)dayBox.getValue();
    }//getDay

    public Month getMonth()
    {
        return monthBox.getValue();
    }//getMonth

    public int getYear()
    {
        return (int)yearBox.getValue();
    }//getYear
}