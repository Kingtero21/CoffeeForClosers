package com.example.android.andrewjustjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity == 10) {
            Toast.makeText(this, "You cannot have more than 10 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {
        EditText getName = (EditText) findViewById(R.id.name_field);
        String nameValue = getName.getText().toString();
        EditText getCard = (EditText)findViewById(R.id.creditcard_field);
        String cardValue = getCard.getText().toString();
        EditText getExpiration = (EditText)findViewById(R.id.expiration_field);
        String expirationValue = getExpiration.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        CheckBox caramelCheckBox = (CheckBox) findViewById(R.id.caramel_checkbox);
        boolean hasCaramel = caramelCheckBox.isChecked();
        CheckBox strawberryCheckBox = (CheckBox) findViewById(R.id.strawberry_checkbox);
        boolean hasStrawberry = strawberryCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate, hasCaramel, hasStrawberry);
        String priceMessage = createOrderSummary(nameValue, price, hasWhippedCream, hasChocolate, hasCaramel, hasStrawberry, cardValue, expirationValue);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
 //   private void displayMessage(String message) {
    //    TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
     //   orderSummaryTextView.setText(message);
   // }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addCaramel, boolean addStrawberry) {
        int basePrice = 5;

        if (addWhippedCream)
                 {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        if (addCaramel) {
            basePrice = basePrice + 1;
        }

        if (addStrawberry) {
            basePrice = basePrice + 1;
        }

        int price = quantity * basePrice;
        return price;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate, boolean addCaramel, boolean addStrawberry, String cardNumber, String expiration) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nAdd caramel? " + addCaramel;
        priceMessage += "\nAdd strawberry? " + addStrawberry;
        priceMessage +=  "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage += "\nThank You!";
        priceMessage = priceMessage + "\n\nCredit Card: " + cardNumber;
        priceMessage = priceMessage + "\nExp. Date: " + expiration;
        return priceMessage;
    }


}
