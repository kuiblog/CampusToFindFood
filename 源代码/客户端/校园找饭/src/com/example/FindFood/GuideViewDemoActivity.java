package com.example.FindFood;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

public class GuideViewDemoActivity extends Activity {
	
    private ViewPager viewPager;  
    private ArrayList<View> pageViews;  
    private ViewGroup main, group;  
    private ImageView imageView;  
    private ImageView[] imageViews; 
	
    /** Called when the activity is first created. */
    @SuppressWarnings("unused")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        LayoutInflater inflater = getLayoutInflater();  
        pageViews = new ArrayList<View>();  
        pageViews.add(inflater.inflate(R.layout.item01, null));  
        pageViews.add(inflater.inflate(R.layout.item02, null));  
        pageViews.add(inflater.inflate(R.layout.item03, null));  
        pageViews.add(inflater.inflate(R.layout.item04, null));
        
  
        imageViews = new ImageView[pageViews.size()];  
        main = (ViewGroup)inflater.inflate(R.layout.main, null);  
        
        // group是R.layou.main中的负责包裹小圆点的LinearLayout.  
        group = (ViewGroup)main.findViewById(R.id.viewGroup);  
  
        viewPager = (ViewPager)main.findViewById(R.id.guidePages);  
  
        for (int i = 0; i < pageViews.size(); i++) {  
            imageView = new ImageView(GuideViewDemoActivity.this);  
            imageView.setLayoutParams(new LayoutParams(20,20));  
            imageView.setPadding(20, 0, 20, 0);  
            imageViews[i] = imageView;  
            if (i == 0) {  
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);  
            } else { 
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
            }  
            group.addView(imageViews[i]);  
        }  
  
        setContentView(main);  
  
        viewPager.setAdapter(new GuidePageAdapter());  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());   
        
        
    }
    
    /** 指引页面Adapter */
    class GuidePageAdapter extends PagerAdapter {  
    	  
        @Override  
        public int getCount() {  
            return pageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  		//销毁加载过的
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(pageViews.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).addView(pageViews.get(arg1));  			//强制类型转换，初始化
            return pageViews.get(arg1);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
    } 
    
    /** 指引页面改监听器 */
    class GuidePageChangeListener implements OnPageChangeListener {  
  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
        	
        }  
  
        @Override  
        public void onPageSelected(int arg0) {  
            for (int i = 0; i < imageViews.length; i++) {  
                imageViews[arg0]  
                        .setBackgroundResource(R.drawable.page_indicator_focused);  
                if (arg0 != i) {  
                    imageViews[i]  
                            .setBackgroundResource(R.drawable.page_indicator);  
                    
                }
                
            }
            if(arg0 == imageViews.length - 1)
            {
            	Intent it=new Intent(GuideViewDemoActivity.this,MainActivity.class);
				it.putExtra("toWhichTab", "0");
				
				startActivity(it);
				finish();
            }
        }  
  
    }  
    
}