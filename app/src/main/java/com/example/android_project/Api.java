/**
 * API, Frontend
 *
 * configuration options
 *
 * @author Shane Dawson <10383355@ucdconnect.ie>
 * @date 22-11-2014
 * class: COMP40370 Practical Android Programming
 */


package com.example.android_project;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("deprecation")
final class Api {
	private static final String address = "http://192.168.0.11:8000/";
	private static String token = "";
	private static Integer id;


	public static String getToken() {
		return token;
	}

	public static void setToken(String newToken) {
		token = newToken;
	}

	public static Integer getID() {
		return id;
	}

	public static void setID(Integer newID) {
		id = newID;
	}

	public static void registerStudent(String username, String password,
									   String email, String firstName, String lastName, TaskCallback callback)
			throws JSONException, MalformedURLException {

		URL url = new URL(address + "register/");
		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put("username", username);
		jsonRequest.put("password", password);
		jsonRequest.put("email", email);
		jsonRequest.put("first_name", firstName);
		jsonRequest.put("last_name", lastName);

		new RequestTask(callback).execute(jsonRequest, url, "POST", token);

	}

	public static void loginStudent(String username, String password, TaskCallback callback)
			throws JSONException, MalformedURLException {

		URL url = new URL(address + "api-token-auth/");
		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put("username", username);
		jsonRequest.put("password", password);
		new RequestTask(callback).execute(jsonRequest, url, "POST", token);

	}

	public static void setTaughtAttendance(int week, int day, Boolean am,
										   Boolean pm, TaskCallback callback) throws JSONException,
			MalformedURLException {

		URL url = new URL(address + "tattendance/");
		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put("week", week);
		jsonRequest.put("day", day);
		jsonRequest.put("am", am.toString());
		jsonRequest.put("pm", pm.toString());

		new RequestTask(callback).execute(jsonRequest, url, "POST", token);

	}

	public static void query(String query, TaskCallback callback) throws
			MalformedURLException {

		URL url = new URL(address + "search/?query=" + query);

		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getTaughtAttendance(TaskCallback callback)
			throws MalformedURLException {

		URL url = new URL(address + "tattendance/");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void setClinicalAttendance(Integer week,
											 String signature, String comments, TaskCallback callback)
			throws JSONException, MalformedURLException {

		URL url = new URL(address + "cattendance/");
		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put("week", week);
		jsonRequest.put("signature", signature);
		jsonRequest.put("comments", comments);

		new RequestTask(callback).execute(jsonRequest, url, "POST", token);

	}

	public static void getSummativeCase(TaskCallback callback)
			throws MalformedURLException {

		URL url = new URL(address + "summative/");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getStudentSummativeCase(TaskCallback callback)
			throws MalformedURLException {

		URL url = new URL(address + "sct/" + id + "/?format=json");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getStudentFormativeCase(TaskCallback callback)
			throws MalformedURLException {

		URL url = new URL(address + "fct/" + id + "/?format=json");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getSelfAssesment(TaskCallback callback)
			throws MalformedURLException {

		URL url = new URL(address + "selfassesment/");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getStudentSelfAssesment(TaskCallback callback, int id)
			throws MalformedURLException {

		URL url = new URL(address + "sa/" + id + "/?format=json");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getStudentTaughtAttendance(TaskCallback callback, int id)
			throws MalformedURLException {

		URL url = new URL(address + "ta/" + id + "/?format=json");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getStudentClinicalAttendance(TaskCallback callback, int id)
			throws MalformedURLException {

		URL url = new URL(address + "ca/" + id + "/?format=json");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getStudentSelfAssesmentCont(TaskCallback callback, int id)
			throws MalformedURLException {

		URL url = new URL(address + "ref/" + id + "/?format=json");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void setSummativeCase(double q1a, double q1b, double q1m, double q2a, double q2b, double q2m, double q3a, double q3b, double q3m, double total, TaskCallback callback) throws JSONException,
			MalformedURLException {

		URL url = new URL(address + "sct/" + getID().toString() + "/");
		JSONObject jsonRequest = new JSONObject();


		jsonRequest.put("question_one_a", q1a);
		jsonRequest.put("question_one_b", q1b);

		jsonRequest.put("question_two_a", q2a);
		jsonRequest.put("question_two_b", q2b);

		jsonRequest.put("question_three_a", q3a);
		jsonRequest.put("question_three_b", q3b);

		jsonRequest.put("question_one_mark", q1m);
		jsonRequest.put("question_two_mark", q2m);
		jsonRequest.put("question_three_mark", q3m);

		jsonRequest.put("total", total);


		new RequestTask(callback).execute(jsonRequest, url, "POST", token);


	}

	public static void setSelfAssesment(int task, String diagnosis,
										String well, String improve, int mark, TaskCallback callback)
			throws JSONException, MalformedURLException {

		URL url = new URL(address + "selfassesment/");
		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put("task", task);
		jsonRequest.put("diag", diagnosis);
		jsonRequest.put("well", well);
		jsonRequest.put("improve", improve);
		jsonRequest.put("mark", mark);

		new RequestTask(callback).execute(jsonRequest, url, "POST", token);


	}

	public static void setSelfAssesmentCont(String q1, String q2,
											Integer q1m, Integer q2m, Integer average, TaskCallback callback)
			throws JSONException, MalformedURLException {

		URL url = new URL(address + "selfassesmentcont/");
		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put("question_one", q1);
		jsonRequest.put("question_two", q2);
		jsonRequest.put("question_one_mark", q1m);
		jsonRequest.put("question_two_mark", q2m);
		jsonRequest.put("mark", average);

		new RequestTask(callback).execute(jsonRequest, url, "POST", token);


	}

	public static void getSelfAssesmentCont(TaskCallback callback)
			throws MalformedURLException {

		URL url = new URL(address + "selfassesmentcont/");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getFormativeCase(TaskCallback callback)
			throws MalformedURLException {

		URL url = new URL(address + "formative/");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void setFormativeCase(Integer case_,
										Integer questionOne, Integer questionTwo, Integer questionThree,
										String comments, TaskCallback callback) throws JSONException,
			MalformedURLException {

		URL url = new URL(address + "fct/" + getID().toString() + "/");
		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put("case", case_);
		jsonRequest.put("question_one_mark", questionOne);
		jsonRequest.put("question_two_mark", questionTwo);
		jsonRequest.put("question_three_mark", questionThree);
		jsonRequest.put("comment", comments);

		new RequestTask(callback).execute(jsonRequest, url, "POST", token);

	}


	public static void getClinicalAttendance(TaskCallback callback)
			throws MalformedURLException {

		URL url = new URL(address + "cattendance/");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void getStudentList(TaskCallback callback)
			throws
			MalformedURLException {

		URL url = new URL(address + "students/");
		new RequestTask(callback).execute(new JSONObject(), url, "GET", token);

	}

	public static void generateKey(TaskCallback callback)
			throws
			MalformedURLException {

		URL url = new URL(address + "key/");
		new RequestTask(callback).execute(new JSONObject(), url, "POST", token);

	}

	public static void checkKey(String key, TaskCallback callback)
			throws JSONException,
			MalformedURLException {

		URL url = new URL(address + "check_key/");
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("key", key);
		new RequestTask(callback).execute(jsonRequest, url, "POST", token);

	}

	public static void getKey(TaskCallback callback)
			throws
			MalformedURLException {

		URL url = new URL(address + "get_key/");
		new RequestTask(callback).execute(new JSONObject(), url, "POST", token);

	}

	public static void isAdmin(TaskCallback callback) throws MalformedURLException {
		URL url = new URL(address + "is_admin/");
		Log.d("ADMIN_TOKEN", token);
		new RequestTask(callback).execute(new JSONObject(), url, "POST", token);

	}


}
