

import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;


    public void makeJSONRequest(final String url, final String data,final Listener callback)
    {
        System.out.println("REQUEST - "+data);
        System.out.println("REQUEST URL - "+url);
        T = new Thread(){
            @Override
            public void run(){
                HttpURLConnection connection = null;
                try {
                    byte[] dt = data.getBytes();
                    connection = (HttpURLConnection) (new URL(url)).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Content-Length", dt.length+"");
                    connection.setRequestProperty("Content-Language", "en-US");
                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                    dos.write(dt);
                    dos.flush();
                    System.out.println("Status: "+connection.getResponseMessage()+" - "+connection.getResponseCode());

//                    DataInputStream dis= new DataInputStream(connection.getInputStream());
//                    System.out.println(dis.readLine());

                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    Gson g = new Gson();
                    CloudResponse response = g.fromJson(isr, CloudResponse.class);
                    if(response==null)
                    {
                        callback.onError("Server response error");
                    }else{
                        if(response.status==0 && response.message.length()>0)
                        {
                            callback.onError(response.message);
                        }else if(response.status==1){
                            callback.onResponse(response);
                        }
                        if(response.release)
                        {
                            callback.onRelease();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(VampireCloud.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getLocalizedMessage());
//                    DataInputStream dis= null;
//                    try {
//                        dis = new DataInputStream(connection.getInputStream());
//                        System.out.println(dis.readLine());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                }
            }
        };
        T.start();
    }

   

    public interface Listener{
        void onResponse(CloudResponse response);
        void onError(String message);
        void onRelease();
    }
}




apply plugin: 'com.android.application'

android {
    compileSdkVersion 25
    buildToolsVersion "23.0.2"
    defaultConfig {
        applicationId ''
        minSdkVersion 16
        targetSdkVersion 23
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    productFlavors {
    }
    useLibrary 'org.apache.http.legacy'
    packagingOptions {
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/NOTICE.txt'
    }
}

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    compile('org.apache.httpcomponents:httpmime:4.3') {
        exclude module: "httpclient"
    }
    compile 'com.google.code.gson:gson:2.3.1'
    compile 'commons-io:commons-io:2.4'
    compile 'org.apache.httpcomponents:httpclient-android:4.3.5.1'
    compile 'commons-codec:commons-codec:1.10'
    compile 'com.google.firebase:firebase-messaging:10.0.1'
    compile 'com.android.support:support-v4:25.1.1'
    compile 'com.android.support:appcompat-v7:25.1.1'
    compile 'com.android.support:support-v13:25.1.1'
    compile 'com.android.support:design:25.1.1'
    compile 'com.android.volley:volley:1.0.0'
    compile 'com.android.support:support-vector-drawable:25.1.1'
    compile 'com.android.support:cardview-v7:25.1.1'
    testCompile 'junit:junit:4.12'
}




apply plugin: 'com.google.gms.google-services'
