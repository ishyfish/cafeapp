/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rhhscaffrontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import rhhscaffbackend.*;
import rhhscontroller.*;
import utils.CardSwitcher;

public class FoodPanel extends JPanel {

    private ArrayList<Consumable> foodList;
    private JTextField[] nameFields;
    private JTextField[] priceFields;
    private JTextField[] descriptionFields;
    private JTextField[] calorieFields;
    private JTextField[] dietaryFields; // Only for PreparedFood
    private JCheckBox[] checkBoxes;
    private JTextField searchField; // Search input field
    private JPanel mainPanel;
    JCheckBox binarySearchCheckBox;
    JRadioButton sort1Button;
    JRadioButton sort2Button;

    public static final String CARD_NAME = "food";
    CardSwitcher switcher = null;
    Controller controller;

    public FoodPanel(CardSwitcher p, Controller c) {
        switcher = p;
        controller = c;
        // I've added this so that it will load the food items only when this "card"
        // becomes visible and every time it becomes visible.
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                // set up the panel
                setUpPanels();
            }

        });
    }

    private void setUpPanels() {
        // load food items into an arrayList, I used a boolean for all vs active only,
        // you don't need to
        this.foodList = controller.loadFood(false);
        // borderlayout is a simple layout with North, south, west, east and center
        // regions for components/panels
        setLayout(new BorderLayout());

        /**
         * ********* NORTH //We're going to have a searchPanel in the north for
         * your to demonstrate your searching. //as this is just a 1-line panel,
         * we'll use something called FlowLayout, side by side ***************
         */
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        binarySearchCheckBox = new JCheckBox("binary");
        searchPanel.add(new JLabel("Search by Name:"));
        sort1Button = new JRadioButton("sort1");
        sort1Button.setSelected(true);
        sort2Button = new JRadioButton("sort2");
        ButtonGroup rbg = new ButtonGroup();
        rbg.add(sort1Button);
        rbg.add(sort2Button);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(binarySearchCheckBox);
        searchPanel.add(sort1Button);
        searchPanel.add(sort2Button);
        add(searchPanel, BorderLayout.NORTH);

        /**
         * ***************** CENTER *****
         * Create the main panel for food items
         * ****************************
         */
        mainPanel = new JPanel();
        // another new layout type. GridBag essentially makes a grid based on certain
        // constraints we give it
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Define column headers with sorting buttons
        String[] headers = { "Enable", "Name", "Price", "Description", "Calories", "Dietary Restrictions" };
        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.anchor = GridBagConstraints.NORTH;

            JButton headerButton = new JButton(headers[i]);
            int columnIndex = i; // Capture the column index for sorting
            headerButton.addActionListener(e -> sortFoodList(columnIndex));
            mainPanel.add(headerButton, gbc);
        }

        // Initialize arrays for fields and checkboxes
        initializeFieldsAndCheckboxes(mainPanel, gbc);

        // Wrap the main panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        /**
         * ***************** SOUTH **
         * Save button at the bottom
         * **************************
         */
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new SaveButtonListener());
        add(saveButton, BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();
        revalidate();
        repaint();

    }

    private void initializeFieldsAndCheckboxes(JPanel mainPanel, GridBagConstraints gbc) {
        nameFields = new JTextField[foodList.size()];
        priceFields = new JTextField[foodList.size()];
        descriptionFields = new JTextField[foodList.size()];
        calorieFields = new JTextField[foodList.size()];
        dietaryFields = new JTextField[foodList.size()]; // Only for PreparedFood
        checkBoxes = new JCheckBox[foodList.size()];

        for (int i = 0; i < foodList.size(); i++) {
            Consumable food = foodList.get(i);
            gbc.gridy = i + 1; // Start adding food items from row 1

            // CheckBox
            checkBoxes[i] = new JCheckBox();
            checkBoxes[i].setSelected(true); // Initially enabled
            gbc.gridx = 0;
            mainPanel.add(checkBoxes[i], gbc);

            // Editable Fields
            nameFields[i] = new JTextField(food.getName());
            gbc.gridx = 1;
            mainPanel.add(nameFields[i], gbc);

            priceFields[i] = new JTextField(String.valueOf(food.getPrice()));
            gbc.gridx = 2;
            mainPanel.add(priceFields[i], gbc);

            descriptionFields[i] = new JTextField(food.getDescription());
            gbc.gridx = 3;
            mainPanel.add(descriptionFields[i], gbc);

            calorieFields[i] = new JTextField(String.valueOf(food.getCalories()));
            gbc.gridx = 4;
            mainPanel.add(calorieFields[i], gbc);

            // Dietary restrictions field only for PreparedFood
            if (food instanceof PreparedFood) {
                dietaryFields[i] = new JTextField(((PreparedFood) food).getDietaryRestrictions());
            } else {
                dietaryFields[i] = new JTextField(); // Empty for other food types
            }
            gbc.gridx = 5;
            mainPanel.add(dietaryFields[i], gbc);
        }
    }

    private void sortFoodList(int columnIndex) {
        // Set the sorting attribute in all Food objects
        for (Consumable food : foodList) {
            food.setSortingAttribute(columnIndex);
        }

        // Now call sort
        // Sorting.selectionSort(foodList);
        if (sort1Button.isSelected())
            controller.selectionSort(foodList);
        else
            controller.bubbleSort(foodList);
        refreshFoodItems();
    }

    private void refreshFoodItems() {
        for (int i = 0; i < foodList.size(); i++) {
            Consumable food = foodList.get(i);

            // Update the existing fields with the new data
            nameFields[i].setText(food.getName());
            priceFields[i].setText(String.valueOf(food.getPrice()));
            descriptionFields[i].setText(food.getDescription());
            calorieFields[i].setText(String.valueOf(food.getCalories()));

            // Update dietary restrictions for PreparedFood
            if (food instanceof PreparedFood) {
                dietaryFields[i].setText(((PreparedFood) food).getDietaryRestrictions());
            } else {
                dietaryFields[i].setText(""); // Clear if not applicable
            }
        }
        revalidate(); // Revalidate the layout
        repaint(); // Repaint the panel
    }

    private class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String searchTerm = searchField.getText().toLowerCase();
            // the found index
            int index = -1;
            if (binarySearchCheckBox.isSelected()) {
                index = controller.binarySearch(foodList, searchTerm);// binary search
                System.out.println(index);
                
            } else {
                // sequential search
                index = controller.sequentialSearch(foodList, searchTerm);
                System.out.println("index: " + index);
            }

            // Scroll to the matching item
            JScrollPane scrollPane = (JScrollPane) getComponent(1); // Access the scroll pane
            JViewport viewport = scrollPane.getViewport();

            // Get the component at the target index
            Component targetComponent = mainPanel.getComponent((index + 1) * 6); // Each food item occupies 6 slots
                                                                                 // (checkbox + 5 fields)

            // Get the location of the target component in the main panel
            Point targetPoint = targetComponent.getLocation();

            // Convert that point to the viewport coordinates
            Point viewPosition = SwingUtilities.convertPoint(mainPanel, targetPoint, viewport.getView());

            // Scroll to the target position
            viewport.setViewPosition(new Point(0, viewPosition.y));
        }
    }

    private class SaveButtonListener implements ActionListener {
        // currently this ONLY updates the original list
        // obviously in here you would have to update your database
        // an alternative might be to forgo the save button entirely and update your DB
        // live
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < foodList.size(); i++) {
                Consumable food = foodList.get(i);
                boolean isEnabled = checkBoxes[i].isSelected();

                // Update food properties based on the input fields
                food.setName(nameFields[i].getText());
                food.setPrice(Double.parseDouble(priceFields[i].getText()));
                food.setDescription(descriptionFields[i].getText());
                food.setCalories(Integer.parseInt(calorieFields[i].getText()));

                if (food instanceof PreparedFood) {
                    ((PreparedFood) food).setDietaryRestrictions(dietaryFields[i].getText());
                }

                System.out.println(food.getName() + " is " + (isEnabled ? "enabled" : "disabled"));
                // Implement further logic as needed (e.g., saving to a database)
            }
        }
    }
}
