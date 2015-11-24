package com.lixue.aibei.chapter7;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class DemoActivity_2 extends ActionBarActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_activity_2);
        initView();
        initData();
    }
    private void initView(){
        listView = (ListView) findViewById(R.id.list);
    }
    private void initData(){
        //还可以通过代码来加载动画
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
//        LayoutAnimationController controller = new LayoutAnimationController(animation);
//        controller.setDelay(0.5f);
//        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        listView.setLayoutAnimation(controller);

        ArrayList<String> arraydata = new ArrayList<String>();
        for (int i = 0;i<100;i++){
            arraydata.add(i,"name" + i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.activity_list_item,android.R.id.text1,arraydata);
        listView.setAdapter(adapter);
    }
}
