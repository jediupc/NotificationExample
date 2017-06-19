package perez.marcos.com.notificationexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button b, b1, b2, b3;

    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button) findViewById(R.id.button);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);

        layout = findViewById(R.id.layout);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Entero que nos permite identificar la notificación
                int mId = 1;
                //Instanciamos Notification Manager
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                // Para la notificaciones, en lugar de crearlas directamente, lo hacemos mediante
                // un Builder/contructor.
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle("Título")
                                .setContentText("Texto de contenido");


                // Creamos un intent explicito, para abrir la app desde nuestra notificación
                Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);

                //El objeto stack builder contiene una pila artificial para la Acitivty empezada.
                //De esta manera, aseguramos que al navegar hacia atrás
                //desde la Activity nos lleve a la home screen.

                //Desde donde la creamos
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                // Añade la pila para el Intent,pero no el intent en sí
                stackBuilder.addParentStack(ResultActivity.class);
                // Añadimos el intent que empieza la activity que está en el top de la pila
                stackBuilder.addNextIntent(resultIntent);

                //El pending intent será el que se ejecute cuando la notificación sea pulsada
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);

                // mId nos permite actualizar las notificaciones en un futuro
                // Notificamos
                mNotificationManager.notify(mId, mBuilder.build());
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos una notificación toast
                //Tenemos que llamar a getApplication context ya que "this" -->
                Toast.makeText(getApplicationContext(), "Soy un toast y sabes implementarme", Toast.LENGTH_LONG).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos una notificación snackbar
                // parentLayout: ViewGroup donde lo queremos mostrar
                // R.string.snackbar_text texto a mostrar definido en strings.xml
                View.OnClickListener myOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                        startActivity(i);
                    }
                };

                Snackbar.make(layout, R.string.snackbar_text, Snackbar.LENGTH_LONG)
                        .setAction(R.string.snackbar_action, myOnClickListener)
                        .show(); // Importante!!! No olvidar mostrar la Snackbar.

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Entero que nos permite identificar la notificación
                int mId = 2;
                //Instanciamos Notification Manager
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                // Creamos un intent explicito, para abrir la app desde nuestra notificaci�n
                Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);

                //El pending intent será el que se ejecute cuando la notificación sea pulsada
                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                getApplicationContext(),
                                1,
                                resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );


                // Para la notificaciones, en lugar de crearlas directamente, lo hacemos mediante
                // un Builder/contructor.
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_launcher)
                                .addAction(R.drawable.ic_launcher, "Accion 1", resultPendingIntent) // #0
                                .addAction(R.drawable.ic_launcher, "Accion 2", resultPendingIntent)  // #1
                                .setContentTitle("Título")
                                .setContentText("Texto de contenido");

                NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                bigTextStyle.setBigContentTitle("BigContentTitle");
                bigTextStyle.setSummaryText("Resumen, lalalalalalala");
                bigTextStyle.bigText(getString(R.string.loremIpsum));

                mBuilder.setStyle(bigTextStyle);


                mBuilder.setContentIntent(resultPendingIntent);


                // mId nos permite actualizar las notificaciones en un futuro
                // Notificamos
                Notification noti = mBuilder.build();

                //O patrón de vibración propio

                noti.vibrate = new long[]{500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500};

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    noti.color = Color.CYAN;
                }

                noti.flags |= Notification.FLAG_INSISTENT;
                noti.flags |= Notification.FLAG_NO_CLEAR;
                noti.flags |= Notification.FLAG_SHOW_LIGHTS;
                mNotificationManager.notify(mId, noti);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
