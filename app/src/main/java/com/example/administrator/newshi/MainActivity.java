package com.example.administrator.newshi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;


public class MainActivity extends Activity {
    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            String result=msg.obj.toString().trim();
            System.out.println(result);
            if(result.equals("yes")) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "登陆成功！", Toast.LENGTH_LONG).show();
            }else{
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "登陆失败！", Toast.LENGTH_LONG).show();
            }
        }
    };
    private Button button,button2;
    private EditText edit1,edit2;
    private ProgressBar progressBar;

    private JSONObject jobj=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)this.findViewById(R.id.button);
        edit1=(EditText)this.findViewById(R.id.login_username);
        edit2=(EditText)this.findViewById(R.id.login_pswd);
        progressBar=(ProgressBar)this.findViewById(R.id.progressBar);
        button2=(Button)this.findViewById(R.id.btn_register);

        progressBar.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String post_str = "uacount=" + edit1.getText().toString().trim() + "&upwd=" + edit2.getText().toString().trim();
                        post_str = post_str + "&flag=yes";
                      //  System.out.println(post_str);
                        String result = new HttpGet(post_str,"userlogincheck.php").startLink();
                        Message msg = handler.obtainMessage();
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                }.start();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Registerx.class));
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
