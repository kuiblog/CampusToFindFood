package com.example.FindFood;
//全局变量定义

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;





import android.app.Application;  
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

public class MyApplication extends Application {

        private int imageFlag;   //是否显示图片标记:1仅在WiFi下显示图片，0为在任何时候显示图片
        private int messageFlag; //是否接受推送通知标记：1接受，0不接受
        private String selectCity; //用户选择的城市。
        private int firstLoginFlag; //用户是否是第一次打开 1：是，0：不是。
        private String accountName = "test";
        private String password = "test";
        private int loginFlag = 0;
        private String sellerName;
        private String sellerPassword;
        private double[] selectedCityLatlon;
        private List<Map<String, String>> listFirst = new ArrayList<Map<String, String>>();
        private int listFirstFlag = 0;  //是否已获得服务器返回的本校周边数据 ，0为未获得，1为已获得
        private String []cId = new String[50];//用户登录后服务器返回的历史订单号
        private String []unDoCId = new String[50];
        private String endPoint = new String();
        private String nameSpace = new String();
        private String cAddress = new String();
        private String []sId = new String[50];
        private int sIdCount;             //卖家获取的订单数量
        private int cIdCount;  			//买家获取的订单数量
        private int sellerService = 0;
        private int conService = 0;
        private int autoCity = 0;  //代表是否已经自动载入历史城市 , 0为未载入
        private int homeMode = 0; //首页学校选择的模式 ，0为手动选择，1位自动定位。
        
        private Object detail;
        private Handler handler;
      
        
        @Override
        public void onCreate() {
                super.onCreate();
                setImageFlag(0);
                setMessageFlag(1);//初始化全局变量
                setLoginFlag(0);
                setAccountName(null);
                double [] ll ={0,0};
                
                setSelectedCityLatlon(ll);
                setSelectCity(null);
                setEndPoint("http://10.1.99.180:8080/WebServiceProject/services/Findfood ");
                setNameSpace("http://10.1.99.180:8080/");
                
              //**消息处理
                handler=new Handler(){

        			public void handleMessage(Message msg){

        			     

        			
        			if(msg.what == 0)
        				Toast.makeText(MyApplication.this, "自动登录成功，您可到更多选项卡--〉登陆选项中关闭该功能", Toast.LENGTH_LONG).show();
        			}
        			

        			 };
                
                //********************自动登录
        		SharedPreferences  share = getSharedPreferences("perference", MODE_PRIVATE);
                String autoflag = share.getString("autologin", "3");
                Log.i("auto",""+autoflag);
                if((""+autoflag).equals("1"))
                {	
                	
                	Log.i("auto","auto");
                	String oldaccountname = share.getString("accountName", "");//根据key寻找值 参数1 key 参数2 如果没有value显示的内容  
            		String oldpassword  = share.getString("accountPassword", "");  
            		try {
        				getAccounter(oldaccountname,oldpassword);
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
                	
                }
                
                //**guide
                
              
                
                
        }
        
        public  String setSellerPassword(String sellerPassword) {
            return this.sellerPassword = sellerPassword;
    	}
        
        public String getSellerPassword() {
            return sellerPassword;
        }
        public  String setPassword(String password) {
            return this.password = password;
    	}
        
        public String getPassword() {
            return password;
        }
        public  String setCAddress(String cAddress) {
            return this.cAddress = cAddress;
    	}
        public String getCAddress() {
            return cAddress;
        }
        
        public  String setNameSpace(String nameSpace) {
            return this.nameSpace = nameSpace;
    	}
        public String getNameSpace() {
            return nameSpace;
        }
        
        public  String setEndPoint(String endPoint) {
            return this.endPoint = endPoint;
    	}
        public String getEndPoint() {
            return endPoint;
        }
        
        public List<Map<String, String>> getListFirst() {
            return listFirst;
        }

        public List<Map<String, String>> setListFirst(List<Map<String, String>> listFirst) {
            return this.listFirst = listFirst;
    	}
        
        public double[] getSelectedCityLatlon() {
            return selectedCityLatlon;
        }

        public double[] setSelectedCityLatlon(double[] selectedCityLatlon) {
            return this.selectedCityLatlon = selectedCityLatlon;
    	}
        public String[] getCId() {
            return cId;
        }

        public  String[] setUnDoCId(String[] unDoCId) {
            return this.unDoCId = unDoCId;
        }
        public String[] getUnDoCId() {
            return unDoCId;
        }
        
        public  String[] setCId(String[] cId) {
            return this.cId = cId;
        }
        public String[] getSId() {
            return sId;
        }

        public  String[] setSId(String[] sId) {
            return this.sId = sId;
        }
        
        public String getSellerName() {
            return sellerName;
        }

        public  String setSellerName(String sellerName) {
            return this.sellerName = sellerName;
    	}
        public String getAccountName() {
            return accountName;
        }

        public  String setAccountName(String accountName) {
            return this.accountName = accountName;
    	}
        
        public String getSelectCity() {
            return selectCity;
        }

        public  String setSelectCity(String selectCity) {
            return this.selectCity = selectCity;
    	}
        public int getLoginFlag() {
            return loginFlag;
    }
        public  int setSellerService(int sellerService) {
            return this.sellerService = sellerService;
    }
        
        public int getSellerService() {
            return sellerService;
    }
        public  int setAutoCity(int autoCity) {
            return this.autoCity = autoCity;
    }
        
        public int getAutoCity() {
            return autoCity;
    }
      
        
        public  int setConService(int conService) {
            return this.conService = conService;
    }
        
        public int getConService() {
            return conService;
    }
        public  int setHomeMode(int homeMode) {
            return this.homeMode = homeMode;
    }
        
        public int getHomeMode() {
            return homeMode;
    }
        
        public  int setSIdCount(int sIdCount) {
            return this.sIdCount = sIdCount;
    }
        
        public int getSIdCount() {
            return sIdCount;
    }
        public  int setCIdCount(int cIdCount) {
            return this.cIdCount = cIdCount;
    }
        
        public int getCIdCount() {
            return cIdCount;
    }
        
        
        public  int setListFirstFlag(int listFirstFlag) {
            return this.listFirstFlag = listFirstFlag;
    }
        
        public int getListFirstFlag() {
            return listFirstFlag;
    }

        public  int setLoginFlag(int loginFlag) {
            return this.loginFlag = loginFlag;
    }
        
        public int getFirstLoginFlag() {
            return firstLoginFlag;
    }
        public  int setFirstLoginFlag(int firstLoginFlag) {
            return this.firstLoginFlag = firstLoginFlag;
    }

        
		public int getImageFlag() {
                return imageFlag;
        }

        public  int setImageFlag(int imageFlag) {
                return this.imageFlag = imageFlag;
        }
        
        public int getMessageFlag() {
            return messageFlag;
    }

    public  int setMessageFlag(int messageFlag) {
            return this.messageFlag = messageFlag;
    }
        
        private static final String NAME = "MyApplication";

//******自动登录
	 public void getAccounter(final String phoneSec,final String password) throws InterruptedException {  
			
			Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
			    
				
				public void run(){ 
				
				
				
				 

			
	        // 命名空间  
			String nameSpace = getNameSpace(); 
			
	      // 调用的方法名称  

	        String methodName = "get";  
	       
	        // EndPoint  

	        String endPoint = getEndPoint();  
	        


	       
	  

	        // 指定WebService的命名空间和调用的方法名  

	       SoapObject rpc = new SoapObject(nameSpace, methodName);  
	     


	        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
	        rpc.addProperty("Conname", phoneSec);  
	        
	        rpc.addProperty("Conpassword", password);  
	       
	  

	        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  

	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
	        
	  

	        envelope.bodyOut = rpc;  
	        Log.i("9","9");
	        // 设置是否调用的是dotNet开发的WebService  

	        envelope.dotNet = true;  
	        Log.i("10","10");
	        // 等价于envelope.bodyOut = rpc;  

	        envelope.setOutputSoapObject(rpc);  
	        

	        HttpTransportSE transport = new HttpTransportSE(endPoint,5000);
	        


	        try {  

	           // 调用WebService  
	        	

	            transport.call(null, envelope);  

	        } catch (Exception e) {  

	            e.printStackTrace();  
	      }  

	  
	        
	      // 获取返回的数据  

	        
			
				try {
					detail = (Object) envelope.getResponse();
				} catch (SoapFault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);
					Thread.interrupted();
					
				}
			
	        Log.i("14",detail.toString());
	        if(detail.toString().equals("NO"))
			{
	        	Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
	        	
			    
			    			}
	        else 
	        {	Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
	        	
	        	setAccountName(phoneSec);
	        	setPassword(password);
	        	
	        	setLoginFlag(1);
	        
		        //Looper.loop(); 
	            JSONObject dataJson;
				try {
				
				dataJson = new JSONObject(detail.toString());
				
	            JSONArray data=dataJson.getJSONArray("oidObject");
	            setCAddress((String) dataJson.get("address"));
	          
	            for(int i = 0;i < Integer.parseInt(""+dataJson.get("count"));i++ )
	            {
	            JSONObject info=data.getJSONObject(i);
	            String id=info.getString(""+i);
	            Log.i("id,denglu",id);
	            cId[i] = id;
	            }
	            setCId(cId);
	            setCIdCount(Integer.parseInt(""+dataJson.get("count")));
	         
	            
	        	 
	        	
	        	
	           
	            
	           
	            
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        	
	        }
	        

	        // 获取返回的结果  

	        

	        Log.i("15","15");

				}
				};
			webserviceThread.start();
			//webserviceThread.join();
		
		}
	 
	
	    
}


//ImageFlag的定义：1仅在WiFi下显示图片，2为在任何时候显示图片