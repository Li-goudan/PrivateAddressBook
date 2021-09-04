package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyHelper myHelper;
    private EditText mEtName;
    private EditText mEtPhone;
    private TextView mTvShow;
    private Button mBtnAdd;
    private Button mBtnQuery;
    private Button mBtnUpdate;
    private Button mBtnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHelper = new MyHelper(this);
        init();//初始化控件
    }

    private void init() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mTvShow = (TextView) findViewById(R.id.tv_show);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnQuery = (Button) findViewById(R.id.btn_query);
        mBtnUpdate = (Button) findViewById(R.id.btn_update);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
        mBtnAdd.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name;
        String phone;
        SQLiteDatabase db;
        ContentValues values;
        switch (v.getId()) {
            case R.id.btn_add: //添加数据
                name = mEtName.getText().toString();
                phone = mEtPhone.getText().toString();
                db = myHelper.getWritableDatabase();//获取可读写SQLiteDatabse对象
                values = new ContentValues();       // 创建ContentValues对象
                values.put("name", name);           // 将数据添加到ContentValues对象
                values.put("phone", phone);
                db.insert("information", null, values);
                Toast.makeText(this, "信息已添加", Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.btn_query: //查询数据
                db = myHelper.getReadableDatabase();
                Cursor cursor = db.query("information", null, null, null, null,
                        null, null);
                if (cursor.getCount() == 0) {
                    mTvShow.setText("");
                    Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                } else {
                    cursor.moveToFirst();
                    mTvShow.setText("Name :  " + cursor.getString(1) +
                            "  ；Tel :  " + cursor.getString(2));
                }
                while (cursor.moveToNext()) {
                    mTvShow.append("\n" + "Name :  " + cursor.getString(1) +
                            "  ；Tel :  " + cursor.getString(2));
                }
                cursor.close();
                db.close();
                break;
            case R.id.btn_update: //修改数据
                db = myHelper.getWritableDatabase();
                values = new ContentValues();       // 要修改的数据
                values.put("phone", phone = mEtPhone.getText().toString());
                db.update("information", values, "name=?",
                        new String[]{mEtName.getText().toString()}); // 更新并得到行数
                Toast.makeText(this, "信息已修改", Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.btn_delete: //删除数据
                db = myHelper.getWritableDatabase();
                db.delete("information", null, null);
                Toast.makeText(this, "信息已删除", Toast.LENGTH_SHORT).show();
                mTvShow.setText("");
                db.close();
                break;
        }
    }
}