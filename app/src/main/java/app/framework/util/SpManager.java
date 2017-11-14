package app.framework.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lgb.xpro.helper.ContextHelper;

import java.util.ArrayList;

/**
 * @author lw
 * sharedpreference数据管理
 */
public class SpManager {

	private static Context context;

	private static SpManager instance;

	public static final String name = "hsh_config"; 							// 基本配置文件名
	public static final String SP_BEAN = "BEAN";								// 实体Bean文件
	public static final String SP_SEARCH_HISTORY = "hsh_search_history";	    // 搜索历史文件
	public static final String SP_GOTONE = "sp_gotone_open";					// 全球通开户临时文件，开户结束之后清空
	public static final String SP_CITY_AREA = "sp_city_area";					// 城市-区域

	private SpManager(){}

	public static synchronized SpManager getInstance() {
		if(null == context) context = ContextHelper.getInstance().getApplicationContext();
		if (instance == null) {
			instance = new SpManager();
		}
		return instance;
	}

	/**
	 * 请用 getInstance() 替换 getInstance(Context ctx) 方法，此方法废弃
     */
	@Deprecated
	public static synchronized SpManager getInstance(Context ctx) {
		return getInstance();
	}

	/**
	 * 保存整形数据
	 * @param fileName 保存数据的文件名
	 * @param k 数据key
	 * @param v 数据值
	 */
	public void writeInt(String fileName, String k, int v) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putInt(k, v);
		if(!editor.commit())
			Toast.makeText(context, "数据写入失败", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 保存布尔值数据
	 * @param fileName 保存数据的文件名
	 * @param k 数据key
	 * @param v 数据值
	 */
	public void writeBoolean(String fileName, String k,
                             boolean v) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putBoolean(k, v);
		if(!editor.commit())
			Toast.makeText(context, "数据写入失败", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 保存字符型数据
	 * @param fileName 保存数据的文件名
	 * @param k 数据key
	 * @param v 数据值
	 */
	public void writeString(String fileName, String k,
                            String v) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString(k, v);
		if(!editor.commit())
			Toast.makeText(context, "数据写入失败", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 保存长整形数据
	 * @param fileName 保存数据的文件名
	 * @param k 数据key
	 * @param v 数据值
	 */
	public void writeLong(String fileName, String k, long v) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putLong(k, v);
		if(!editor.commit())
			Toast.makeText(context, "数据写入失败", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 保存Java bean对象数据，暂不支持Flie,Image文件流
	 * @param fileName
	 * @param k
	 * @param obj
	 */
	public void writeObject(String fileName, String k, Object obj) {
		if(null == obj){
			obj = new Object();
		}
		Gson gson = new Gson();
		String objectToString = gson.toJson(obj);
		byte[] buf = objectToString.getBytes();
		writeString(fileName,k, Base64.encodeToString(buf, Base64.DEFAULT));
	}

	/**
	 * 读取Java bean对象数据，暂不支持Flie,Image文件流
	 * @param fileName
	 * @param k
	 * @param objClass
	 * 			保存对象的Class
	 */
	public Object readObject(String fileName, String k, Class<? extends Object> objClass) {
		String base64String = readString(fileName,k);
		if(TextUtils.isEmpty(base64String) || null == objClass){
			return null;
		}
		Gson gson = new Gson();
		byte[] buf = Base64.decode(base64String, Base64.DEFAULT);
		Object obj = gson.fromJson(new String(buf),objClass);
		return null != obj ? obj : new Object();
	}

	public <T> ArrayList<T> fromJsonList(String fileName, String k, Class<T> cls) {
		String base64String = readString(fileName,k);
		if(TextUtils.isEmpty(base64String) || null == cls){
			return null;
		}
		Gson gson = new Gson();
		byte[] buf = Base64.decode(base64String, Base64.DEFAULT);

		ArrayList<T> mList = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(new String(buf)).getAsJsonArray();
		for(final JsonElement elem : array){
			mList.add(gson.fromJson(elem, cls));
		}
		return mList;
	}

	/**
	 * 读取整形数据
	 * @param fileName 保存数据的文件名
	 * @param k 获取数据的key
	 * @return 返回整形值
	 */
	public int readInt(String fileName, String k) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return preference.getInt(k, 0);
	}

	/**
	 * 读取整形数据
	 * @param fileName 保存数据的文件名
	 * @param k 获取数据的key
	 * @param defv 默认值
	 * @return 若文件中获取不到整型值 则返回默认值
	 */
	public int readInt(String fileName, String k,
                       int defv) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return preference.getInt(k, defv);
	}

	/**
	 * 读取长整形数据
	 * @param fileName 保存数据的文件名
	 * @param k 获取数据的key
	 * @param defv 默认值
	 * @return 若文件中获取不到长整型值 则返回默认值
	 */
	public long readLong(String fileName, String k,
                         int defv) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return preference.getLong(k, defv);
	}

	/**
	 * 读取布尔值数据
	 * @param fileName 保存数据的文件名
	 * @param k 获取数据的key
	 * @return 返回布尔值
	 */
	public boolean readBoolean(String fileName, String k) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return preference.getBoolean(k, false);
	}

	/**
	 * 读取布尔值数据
	 * @param fileName 保存数据的文件名
	 * @param k 获取数据的key
	 * @param defBool 默认值
	 * @return 若文件中获取不到布尔值 则返回默认值
	 */
	public boolean readBoolean(String fileName,
                               String k, boolean defBool) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return preference.getBoolean(k, defBool);
	}

	/**
	 * 读取字符型数据
	 * @param fileName 保存数据的文件名
	 * @param k 获取数据的key
	 * @return 返回字符型数据
	 */
	public String readString(String fileName, String k) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return preference.getString(k, "");
	}

	/**
	 * 读取字符型数据
	 * @param fileName 保存数据的文件名
	 * @param k 获取数据的key
	 * @param defV 默认值
	 * @return 若文件中获取不到字符数据  则返回默认值
	 */
	public String readString(String fileName, String k,
                             String defV) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return preference.getString(k, defV);
	}

	/**
	 * 清楚指定文件中的某条记录
	 * @param fileName 保存数据的文件名
	 * @param k 需要清楚的数据key
	 */
	public void remove(String fileName, String k) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.remove(k);
		if(!editor.commit())
			Toast.makeText(context, "数据移除失败", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 清楚指定文件数据
	 * @param fileName 保存数据的文件名
	 */
	public void clean(String fileName) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.clear();
		if(!editor.commit())
			Toast.makeText(context, "数据清空失败", Toast.LENGTH_SHORT).show();
	}

}
