package com.example.uidesign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




/*
 ---------------------------------------------登陆页面-------------------------------
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText user,password;
    private Button cancel,login;
    private Context context;
    private String user_tv, password_tv;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        WorkThread wt=new WorkThread();
        wt.start();//调动子线程
    }

    private void init() {
        context =LoginActivity.this;
       user= (EditText)findViewById(R.id.user);
       password= (EditText)findViewById(R.id.password);
       cancel= (Button)findViewById(R.id.cancel);
       login= (Button)findViewById(R.id.login);
        cancel.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cancel:
                user.setText("");
                password.setText("");
                Toast.makeText(context,"点击了取消按钮",0).show();
                break;
            case R.id.login:
                user_tv=user.getText().toString().trim();
                password_tv=password.getText().toString().trim();
                Message m=handler.obtainMessage();//获取事件
                Bundle b=new Bundle();
                b.putString("name",user_tv);
                b.putString("pass",password_tv);//以键值对形式放进 Bundle中
                m.setData(b);
                m.what=0;
                handler.sendMessage(m);//把信息放到通道中

                break;

        }
    }

    class WorkThread extends  Thread{
        @Override
        public  void run(){
            Looper.prepare();
            handler=new Handler(){
                @Override
                public  void handleMessage(Message m){
                    switch (m.what) {
                        case 0:
                        Bundle b = m.getData();//得到与信息对用的Bundle
                        String name = b.getString("name");//根据键取值
                        String pass = b.getString("pass");
                        DbUtil db = new DbUtil();//调用数据库查询类
                        String ret = db.QuerySQL(name, pass);//得到返回值
                        //if (ret.equals("1"))//为1，页面跳转，登陆成功
                        //boolean uname = user.getText().toString().equals("syl")||user.getText().toString().equals("dsy")||user.getText().toString().equals("tyk");
                        //boolean upwd = password.getText().toString().equals("123")||password.getText().toString().equals("456")||password.getText().toString().equals("789");

                        if(ret.equals("1"))
                        {
                           startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();//显示提示框
                            return;
                        }
                        Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            };
            Looper.loop();//Looper循环，通道中有数据执行，无数据堵塞
        }
    }
}
