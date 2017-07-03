package com.example.FindFood;
//ȫ�ֱ�������

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

        private int imageFlag;   //�Ƿ���ʾͼƬ���:1����WiFi����ʾͼƬ��0Ϊ���κ�ʱ����ʾͼƬ
        private int messageFlag; //�Ƿ��������֪ͨ��ǣ�1���ܣ�0������
        private String selectCity; //�û�ѡ��ĳ��С�
        private int firstLoginFlag; //�û��Ƿ��ǵ�һ�δ� 1���ǣ�0�����ǡ�
        private String accountName = "test";
        private String password = "test";
        private int loginFlag = 0;
        private String sellerName;
        private String sellerPassword;
        private double[] selectedCityLatlon;
        private List<Map<String, String>> listFirst = new ArrayList<Map<String, String>>();
        private int listFirstFlag = 0;  //�Ƿ��ѻ�÷��������صı�У�ܱ����� ��0Ϊδ��ã�1Ϊ�ѻ��
        private String []cId = new String[50];//�û���¼����������ص���ʷ������
        private String []unDoCId = new String[50];
        private String endPoint = new String();
        private String nameSpace = new String();
        private String cAddress = new String();
        private String []sId = new String[50];
        private int sIdCount;             //���һ�ȡ�Ķ�������
        private int cIdCount;  			//��һ�ȡ�Ķ�������
        private int sellerService = 0;
        private int conService = 0;
        private int autoCity = 0;  //�����Ƿ��Ѿ��Զ�������ʷ���� , 0Ϊδ����
        private int homeMode = 0; //��ҳѧУѡ���ģʽ ��0Ϊ�ֶ�ѡ��1λ�Զ���λ��
        
        private Object detail;
        private Handler handler;
      
        
        @Override
        public void onCreate() {
                super.onCreate();
                setImageFlag(0);
                setMessageFlag(1);//��ʼ��ȫ�ֱ���
                setLoginFlag(0);
                setAccountName(null);
                double [] ll ={0,0};
                
                setSelectedCityLatlon(ll);
                setSelectCity(null);
                setEndPoint("http://10.1.99.180:8080/WebServiceProject/services/Findfood ");
                setNameSpace("http://10.1.99.180:8080/");
                
              //**��Ϣ����
                handler=new Handler(){

        			public void handleMessage(Message msg){

        			     

        			
        			if(msg.what == 0)
        				Toast.makeText(MyApplication.this, "�Զ���¼�ɹ������ɵ�����ѡ�--����½ѡ���йرոù���", Toast.LENGTH_LONG).show();
        			}
        			

        			 };
                
                //********************�Զ���¼
        		SharedPreferences  share = getSharedPreferences("perference", MODE_PRIVATE);
                String autoflag = share.getString("autologin", "3");
                Log.i("auto",""+autoflag);
                if((""+autoflag).equals("1"))
                {	
                	
                	Log.i("auto","auto");
                	String oldaccountname = share.getString("accountName", "");//����keyѰ��ֵ ����1 key ����2 ���û��value��ʾ������  
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

//******�Զ���¼
	 public void getAccounter(final String phoneSec,final String password) throws InterruptedException {  
			
			Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
			    
				
				public void run(){ 
				
				
				
				 

			
	        // �����ռ�  
			String nameSpace = getNameSpace(); 
			
	      // ���õķ�������  

	        String methodName = "get";  
	       
	        // EndPoint  

	        String endPoint = getEndPoint();  
	        


	       
	  

	        // ָ��WebService�������ռ�͵��õķ�����  

	       SoapObject rpc = new SoapObject(nameSpace, methodName);  
	     


	        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
	        rpc.addProperty("Conname", phoneSec);  
	        
	        rpc.addProperty("Conpassword", password);  
	       
	  

	        // ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾  

	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
	        
	  

	        envelope.bodyOut = rpc;  
	        Log.i("9","9");
	        // �����Ƿ���õ���dotNet������WebService  

	        envelope.dotNet = true;  
	        Log.i("10","10");
	        // �ȼ���envelope.bodyOut = rpc;  

	        envelope.setOutputSoapObject(rpc);  
	        

	        HttpTransportSE transport = new HttpTransportSE(endPoint,5000);
	        


	        try {  

	           // ����WebService  
	        	

	            transport.call(null, envelope);  

	        } catch (Exception e) {  

	            e.printStackTrace();  
	      }  

	  
	        
	      // ��ȡ���ص�����  

	        
			
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
	        

	        // ��ȡ���صĽ��  

	        

	        Log.i("15","15");

				}
				};
			webserviceThread.start();
			//webserviceThread.join();
		
		}
	 
	
	    
}


//ImageFlag�Ķ��壺1����WiFi����ʾͼƬ��2Ϊ���κ�ʱ����ʾͼƬ