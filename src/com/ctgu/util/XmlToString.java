package com.ctgu.util;

import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ctgu.model.Book;
import com.ctgu.model.Course;
import com.ctgu.model.Course1;
import com.ctgu.model.Lost_Find_Message;
import com.ctgu.model.MBookDetails;
import com.ctgu.model.Message;
import com.ctgu.model.MyCourse;
import com.ctgu.model.MyMessages;
import com.ctgu.model.Review;
import com.ctgu.model.Score;
import com.ctgu.model.TransactionsGoods;
import com.ctgu.serve.MyServe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.nodes.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import android.annotation.SuppressLint;

import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.OutputStream;

public class XmlToString {

	@SuppressLint("NewApi")
	public static ArrayList<Course> PingjiaoTO(String html) {

		String course = null, teacher = null, pingjiaoOperate = null;
		String pingjiaoOperateId = "";
		ArrayList<Course> courses = new ArrayList<Course>();
		html = html.substring(html.indexOf("<table class=\"GridViewStyle\""), html.length());
		int j = 1;
		Document docc = null;
		docc = Jsoup.parse(html, "UTF-8");
		Elements linkss = docc.select("tr > td");
		for (Element l : linkss) {
			switch (j) {
			case 4:
				course = l.text();
				break;
			case 5:
				teacher = l.text();

				break;
			case 6:
				pingjiaoOperate = l.text();
				break;
			case 7:
				if (!(l.text().isEmpty() || l.text() == "")) {
					String s =l.toString();
					String s1 =s.substring(s.indexOf("id=")+3);
					 s = s1.substring(0,s1.indexOf("'"));
					pingjiaoOperateId =s;
				}
				courses.add(new Course(course, pingjiaoOperate, pingjiaoOperateId, teacher));
				course = pingjiaoOperate = pingjiaoOperateId = teacher = "";
				break;
			}
			j++;
			if (j > 7) {
				j = 1;
			}

		}
		return courses;

	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Score> scoreTo(String html) {
		int year = 0;
		int semester = 0;
		String course = null;
		float credit = 0;
		String examType = null;
		String score = null;
		int i = 1;
		Document doc = null;
		try {
			html = html.substring(html.indexOf("<table class=\"GridViewStyle\""), html.length());
			doc = Jsoup.parse(html);
		} catch (Exception e) {
			MyServe my = new MyServe();
			my.setCtgu_login(false);
		}
		Elements chengji = doc.select("tr>td");
		ArrayList<Score> scoreList = new ArrayList<Score>();
		for (Element l : chengji) {
			switch (i) {
			case 1:
				year = Integer.parseInt(l.text());
				break;
			case 2:
				semester = Integer.parseInt(l.text());
				break;
			case 3:
				course = l.text();
				break;
			case 4:
				credit = Float.parseFloat(l.text());
				break;
			case 5:
				examType = l.text();
				break;
			case 6:
				score = l.text();
				break;
			case 7:
				credit = Float.parseFloat(l.text());
				scoreList.add(new Score(year, semester, course, credit, examType, score, credit));
				break;
			}

			i++;// 7是因为每一伊奥有7个属性
			if (i > 7) {
				i = 1;
			}
		}
		Collections.sort(scoreList);// 在score中实现了Comparable接口

		return scoreList;

	}

	public static String getLibraryPage(String html) {
		String data = null;
		Document doc = Jsoup.parse(html);
		Elements links1 = doc.select("span[id=ctl00_ContentPlaceHolder1_dplblfl1]");
		for (Element l : links1) {
			data = l.text();
		}
		return data;
	}

	public static Elements pingjiao_get_data(String html) {
		Document doc = Jsoup.parse(html);
		Elements data = doc.select("input");
		return data;
	}

	public static ArrayList<Message> get_message_board(InputStream is) {
		int id = 0, read = 0, good = 0, sort = 0, discuss = 0;
		String user = null;
		String time = null;
		String content = null;
		String temp;
		ArrayList<Message> message_board = null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					message_board = new ArrayList<Message>();
					break;

				case XmlPullParser.START_TAG:
					if ("message".equals(parser.getName())) {
						id = Integer.valueOf(parser.getAttributeValue(0));

					} else if ("user".equals(parser.getName())) {
						user = parser.nextText();

					} else if ("content".equals(parser.getName())) {
						content = parser.nextText();

					} else if ("time".equals(parser.getName())) {
						time = parser.nextText();
						time = getTimeTag(time);
					} else if ("read".equals(parser.getName())) {
						temp = parser.nextText();
						read = Integer.valueOf(temp);
					} else if ("good".equals(parser.getName())) {
						temp = parser.nextText();
						good = Integer.valueOf(temp);
					} else if ("sort".equals(parser.getName())) {
						temp = parser.nextText();
						sort = Integer.valueOf(temp);
					} else if ("discuss".equals(parser.getName())) {
						temp = parser.nextText();
						discuss = Integer.valueOf(temp);
					}
					break;
				case XmlPullParser.END_TAG:
					if ("message".equals(parser.getName())) {
						message_board.add(new Message(content, id, time, user, read, good, sort, discuss));
					}
					break;
				}
				event = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return message_board;
	}

	public static ArrayList<Lost_Find_Message> get_lost_message(InputStream is) {
		int id = 0;
		String title = null, keyword = null, time = null;
		ArrayList<Lost_Find_Message> list_lost_message = new ArrayList<Lost_Find_Message>();
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					if ("lost".equals(parser.getName())) {
						id = Integer.valueOf(parser.getAttributeValue(0));
					} else if ("title".equals(parser.getName())) {
						title = parser.nextText();

					} else if ("keyword".equals(parser.getName())) {
						keyword = parser.nextText();

					} else if ("date_time".equals(parser.getName())) {
						time = parser.nextText();

						time = getTimeTag(time);

					}

					break;
				case XmlPullParser.END_TAG:
					if ("lost".equals(parser.getName())) {
						list_lost_message.add(new Lost_Find_Message(id, keyword, time, title));
					}
					break;
				}
				event = parser.next();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return list_lost_message;
	}

	public static void save_xml_course(ArrayList<MyCourse> mycourse, OutputStream out) {
		XmlSerializer serializer = Xml.newSerializer();
		int i = 0;
		try {
			serializer.setOutput(out, "UTF-8");
			serializer.startDocument("UTF-8", true);
			serializer.startTag(null, "courses");
			for (MyCourse course : mycourse) {
				serializer.startTag(null, "course");
				serializer.attribute(null, "id", String.valueOf(i));

				serializer.startTag(null, "name");
				serializer.text(course.getName());
				serializer.endTag(null, "name");

				serializer.startTag(null, "place");
				serializer.text(course.getPlace());
				serializer.endTag(null, "place");

				serializer.startTag(null, "time");
				serializer.text(course.getTime());
				serializer.endTag(null, "time");

				serializer.startTag(null, "teacher");
				serializer.text(course.getTeacher());
				serializer.endTag(null, "teacher");

				serializer.endTag(null, "course");
				i++;
			}
			serializer.endTag(null, "courses");
			serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void save_xml_sorce(ArrayList<Score> mycourse, OutputStream out) {
		XmlSerializer serializer = Xml.newSerializer();
		int i = 0;
		try {
			serializer.setOutput(out, "UTF-8");
			serializer.startDocument("UTF-8", true);
			serializer.startTag(null, "scores");
			for (Score course : mycourse) {
				serializer.startTag(null, "course");
				serializer.startTag(null, "mycourse");
				serializer.text(course.getCourse());
				serializer.endTag(null, "mycourse");

				serializer.startTag(null, "score");
				serializer.text(course.getScore());
				serializer.endTag(null, "score");

				serializer.startTag(null, "year");
				serializer.text(String.valueOf(course.getYear()));
				serializer.endTag(null, "year");

				serializer.startTag(null, "semester");
				serializer.text(String.valueOf(course.getSemester()));
				serializer.endTag(null, "semester");

				serializer.startTag(null, "examtype");
				serializer.text(course.getExamType());
				serializer.endTag(null, "examtype");

				serializer.endTag(null, "course");
				i++;
			}
			serializer.endTag(null, "scores");
			serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static ArrayList<MyCourse> xml_to_mycourse(InputStream is) {
		String name = ".", place = ".", time = ".", teacher = ".";
		ArrayList<MyCourse> mycourse_array = new ArrayList<MyCourse>();
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "UTF-8");// 为Pull解析器设置要解析的XML数据
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					if ("courses".equals(parser.getName())) {

					}
					break;
				case XmlPullParser.START_TAG:
					if ("name".equals(parser.getName())) {
						name = parser.nextText();
					} else if ("place".equals(parser.getName())) {
						place = parser.nextText();

					} else if ("time".equals(parser.getName())) {
						time = parser.nextText();

					} else if ("teacher".equals(parser.getName())) {
						teacher = parser.nextText();

					}
					break;
				case XmlPullParser.END_TAG:
					if ("course".equals(parser.getName())) {
						mycourse_array.add(new MyCourse(name, place, time, teacher));
					}
					break;
				}
				event = parser.next();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return mycourse_array;
	}

	@SuppressLint("NewApi")
	public static String getStudentName(String html) {
		String name = new String();
		Document doc = Jsoup.parse(html);
		Elements links = doc.select("span[id=ctl00_lblSignIn]");
		for (Element l : links) {
			name = l.text();
		}
		if (name.isEmpty()) {
			return name;

		} else {
			return name.replaceAll("\\d", "");
		}
	}

	public static String getCtguLoginError(String html) {
		String error = null;
		Document doc = Jsoup.parse(html);
		Elements linkss = doc.select("font[color=Red]");
		int i = 0;
		for (Element l : linkss) {
			error = l.text();
			i++;
		}
		return error;
	}

	public static ArrayList<MyCourse> courseHtmlToArray(String html) {
		ArrayList<MyCourse> course = new ArrayList<MyCourse>();
		String temp;
		int i = 0;
		try {
			html = html.substring(html.indexOf("<table class=\"GridViewStyle\""), html.length());
			Document doc = Jsoup.parse(html);
			Elements links_class = doc.select("tr>td");
			for (Element links_class1 : links_class) {

				temp = links_class1.toString();

				String name = ".";
				String place = ".";
				String teacher = ".";
				String time = ".";
				if (i % 8 == 0) {

				} else {
					int first = temp.indexOf("<br />");
					if (first != -1) {

						name = temp.substring(4, first);

						temp = temp.substring(first, temp.length());

						first = temp.indexOf("&nbsp");
						place = temp.substring(6, first);
						temp = temp.substring(first + 1, temp.length());

						first = temp.indexOf("<br />");
						time = temp.substring(5, first);
						time = time.replace("&nbsp;", "");

						temp = temp.substring(first + 6, temp.length());

						first = temp.indexOf("等");

						teacher = temp.substring(0, first);

						temp = temp.substring(first + 6, temp.length());

						if (temp.length() > 20) {

							first = temp.indexOf("<br />");
							name = name + "+" + temp.substring(1, first);

							temp = temp.substring(first, temp.length());

							first = temp.indexOf("&nbsp");
							place = place + "+" + temp.substring(6, first);
							temp = temp.substring(first + 1, temp.length());

							first = temp.indexOf("<br />");
							time = time + "+" + temp.substring(5, first);
							time = time.replace("&nbsp;", "");

							temp = temp.substring(first + 6, temp.length());

							first = temp.indexOf("等");

							teacher = teacher + "+" + temp.substring(0, first);

						}
					}
				}
				course.add(new MyCourse(name, place, time, teacher));
				i++;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return course;

	}

	public static ArrayList<Review> xml_to_review(InputStream is) {
		String discuss = null, content = null, time = null, user_review = null, user = null, id = null;
		ArrayList<Review> reviewArr = new ArrayList<Review>();
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "UTF-8");// 为Pull解析器设置要解析的XML数据
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					if ("reviews".equals(parser.getName())) {

					}
					break;
				case XmlPullParser.START_TAG:
					if ("discuss".equals(parser.getName())) {
						discuss = parser.nextText();
					} else if ("user".equals(parser.getName())) {
						user = parser.nextText();

					} else if ("time".equals(parser.getName())) {
						time = parser.nextText();
						time = getTimeTag(time);
					} else if ("content".equals(parser.getName())) {
						content = parser.nextText();

					} else if ("user_review".equals(parser.getName())) {
						user_review = parser.nextText();

					} else if ("review".equals(parser.getName())) {
						id = parser.getAttributeValue(0);

					}
					break;
				case XmlPullParser.END_TAG:
					if ("review".equals(parser.getName())) {
						reviewArr.add(new Review(discuss, content, time, user_review, user, id));
					}
					break;
				}
				event = parser.next();
			}

		} catch (Exception e) {
			// e.printStackTrace();

		}

		return reviewArr;
	}

	public static ArrayList<MyMessages> xml_to_myMessage(InputStream is) {
		String discuss = null, content = null, time = null, content_id = null, user = null, id = null;
		ArrayList<MyMessages> messageArr = new ArrayList<MyMessages>();
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "UTF-8");// 为Pull解析器设置要解析的XML数据
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					if ("reviews".equals(parser.getName())) {

					}
					break;
				case XmlPullParser.START_TAG:
					if ("discuss".equals(parser.getName())) {
						discuss = parser.nextText();
					} else if ("user".equals(parser.getName())) {
						user = parser.nextText();

					} else if ("time".equals(parser.getName())) {
						time = parser.nextText();
						time = getTimeTag(time);

					} else if ("content".equals(parser.getName())) {
						content = parser.nextText();

					} else if ("content_id".equals(parser.getName())) {
						content_id = parser.nextText();

					} else if ("review".equals(parser.getName())) {
						id = parser.getAttributeValue(0);

					}
					break;
				case XmlPullParser.END_TAG:
					if ("review".equals(parser.getName())) {

						messageArr.add(new MyMessages(id, discuss, user, content, content_id, time));
					}
					break;
				}
				event = parser.next();
			}

		} catch (Exception e) {

		}

		return messageArr;
	}

	public static ArrayList<Book> xml_to_libraray(String data) {
		String author = null, bookTitle = null, publisher = null, publicationYear = null, reserved = null, available = null, url = null, callNumber = null;
		ArrayList<Book> bookArr = new ArrayList<Book>();
		Document docc = null;
		docc = Jsoup.parse(data);
		Elements links = docc.select("tbody td");
		int i = 1;
		for (Element l : links) {
			switch (i) {
			case 2:
				bookTitle = l.text();
				docc = Jsoup.parse(l.toString(), "UTF-8");
				Elements link = docc.select("a");
				url = link.get(0).attr("href");
				break;
			case 3:
				author = l.text();
				break;
			case 4:
				publisher = l.text();
				break;
			case 5:
				publicationYear = l.text();
				break;
			case 6:
				callNumber = l.text();
				break;
			case 7:
				reserved = l.text();
				break;
			case 8:
				available = l.text();
				bookArr.add(new Book(author, bookTitle, publisher, publicationYear, reserved, available, url, callNumber));
				break;
			case 9:
				i = 0;
				break;
			}

			i++;
		}

		return bookArr;
	}

	public static ArrayList<MBookDetails> getBookDetails(String string) {
		String place = null, status = null, indexNumber = null;
		ArrayList<MBookDetails> books = new ArrayList<MBookDetails>();
		int j = 0;
		Document docc = null;
		docc = Jsoup.parse(string, "UTF-8");
		Elements linkss = docc.select("tr > td");

		for (int i = 6; i < linkss.size(); i++) {
			Element l = linkss.get(i);
			switch (j) {
			case 1:
				place = l.text();
				break;
			case 2:
				indexNumber = l.text();
				break;
			case 6:
				status = l.text();
				books.add(new MBookDetails(place, status, indexNumber));
				break;
			}

			j++;// 7是因为每一伊奥有7个属性
			if (j > 7) {
				j = 1;
			}

		}
		return books;
	}

	public static String getBookReadTims(String string) {
		String times = "";
		Document docc = null;
		docc = Jsoup.parse(string, "UTF-8");
		Elements links = docc.select("span[id=ctl00_ContentPlaceHolder1_blclbl]");

		for (Element l : links) {
			times = l.text();
		}
		return times;
	}

	public static ArrayList<TransactionsGoods> xml_to_tranion(InputStream is) {
		String user = null, countent = null, time = null, id = null, phone = null, picurl = null, read = null, discuss = null;
		ArrayList<TransactionsGoods> messageArr = new ArrayList<TransactionsGoods>();
		XmlPullParser parser = Xml.newPullParser();
		try {

			parser.setInput(is, "UTF-8");// 为Pull解析器设置要解析的XML数据
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					if ("tranions".equals(parser.getName())) {

					}
					break;
				case XmlPullParser.START_TAG:
					if ("user".equals(parser.getName())) {
						user = parser.nextText();
					} else if ("countent".equals(parser.getName())) {
						countent = parser.nextText();
					} else if ("time".equals(parser.getName())) {
						time = parser.nextText();
						time = getTimeTag(time);
					} else if ("phone".equals(parser.getName())) {
						phone = parser.nextText();
					} else if ("picurl".equals(parser.getName())) {
						picurl = parser.nextText();
					} else if ("read".equals(parser.getName())) {
						read = parser.nextText();
					} else if ("discuss".equals(parser.getName())) {
						discuss = parser.nextText();
					} else if ("tranion".equals(parser.getName())) {
						id = parser.getAttributeValue(0);

					}
					break;
				case XmlPullParser.END_TAG:
					if ("tranion".equals(parser.getName())) {
						messageArr.add(new TransactionsGoods(Integer.valueOf(id), user, countent, time, picurl, phone, read, discuss));
					}
					break;
				}
				event = parser.next();
			}

		} catch (Exception e) {

		}

		return messageArr;
	}

	public static String getTimeTag(String times) {
		long now = System.currentTimeMillis();
		String t = String.valueOf(times) + "000";
		long mytime = Long.valueOf(t) - 28800000;
		long a = (long) (now - mytime);
		if (a < 60000) {
			return "刚刚";
		} else if (getTime(now, "dd").equals(getTime(mytime, "dd"))) {
			if (getTime(now, "HH").equals(getTime(mytime, "HH"))) {
				String mm = Integer.valueOf(getTime(now, "mm")) - Integer.valueOf(getTime(mytime, "mm")) + "分钟";
				return mm + "前";
			} else {
				String hour = Integer.valueOf(getTime(now, "HH")) - Integer.valueOf(getTime(mytime, "HH")) + "小时";
				return hour + "前";
			}
		} else {

			return getTime(mytime, "MM-dd");
		}

	}

	public static String getTime(long time, String tag) {
		SimpleDateFormat df = new SimpleDateFormat(tag);
		String mytime = df.format(time);
		return mytime;
	}

	public static InputStream getStringStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
