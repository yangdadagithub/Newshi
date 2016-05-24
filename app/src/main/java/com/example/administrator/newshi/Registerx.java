package com.example.administrator.newshi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class Registerx extends Activity {

    private EditText edit1,edit2,edit3,edit4,edit5;
    private Button button;
    private ProgressBar progressBar;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressBar.setVisibility(View.GONE);
            String result="";
            if(msg.obj.toString().trim().equals("yes"))
                result="注册成功";
            else
                result="注册失败";
            Toast.makeText(Registerx.this,result,Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerx);
        edit1=(EditText)this.findViewById(R.id.rgs_username);
        edit2=(EditText)this.findViewById(R.id.rgs_pswd);
        edit3=(EditText)this.findViewById(R.id.rgs_name);
        edit4=(EditText)this.findViewById(R.id.rgs_idnumber);
        edit5=(EditText)this.findViewById(R.id.rgs_phone);
        button=(Button)this.findViewById(R.id.rgs_sure);
        progressBar=(ProgressBar)this.findViewById(R.id.rgs_progressBar);

        progressBar.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String postStr = "uname="+edit3.getText().toString().trim()+"&uacount="+edit1.getText().toString().trim()+"&upwd="+edit2.getText().toString().trim()+"&flag=yes"+"&uidnumber="+edit4.getText().toString().trim()+"&uphone="+edit5.getText().toString().trim();
                        String result = new HttpGet(postStr, "adduser.php").startLink();

                        Message msg = handler.obtainMessage();
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });
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

