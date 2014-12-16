package com.ctgu.base;

import android.annotation.SuppressLint;

public final class C {

	public static final class api {
		public static final String baseCtgu = "http://210.42.38.26:84/jwc_glxt/";
		public static final String checkCode = "ValidateCode.aspx";
		public static final String ctguLogin = "login.aspx";
		public static final String __EVENTVALIDATION = "/wEWBQKOmrqLAwKl1bKzCQKC3IeGDAK1qbSRCwLO44u1DVzfq830wXTY29pyqB1kTMdgWLfG";
		public static final String __VIEWSTATE = "/wEPDwUKMTQ4NjM5NDA3OWQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFCGJ0bkxvZ2luU077LK9itKNe3fhI7aoZZ+S5Ryo=";
		public static final String loginOut = "Login.aspx?xttc=1";
		public static final String getScore = "Student_Score/Score_Query.aspx";
		public static final String getCourse = "Course_Choice/Course_Schedule.aspx";

		public static final String getLostGoods = "get_lost_goods.php";
		public static final String getFindGoods = "get_find_goods.php";
		public static final String bbangnetBase = "http://www.bbangnet.com/ctguhelp/ctguhelp/";
		public static final String getOneGoods = "get_lost_find_one.php";
		public static final String addGoods = "add_goods.php";

		public static final String baseCtguHelp = "http://www.ctguhelp.com/ctguhelp/ctguhelp3/";
		public static final String baseimgae = baseCtguHelp + "get_image.php?picurl=";
		public static final String getMessageBoard = "new_get_message_board.php";
		public static final String userLogin = "my_user_login.php";
		public static final String register = "set_my_user.php";
		public static final String get_version = "get_version_code.php";
		public static final String send_message = "new_set_message_board.php";
		public static final String setMyUser = "set_my_user.php";
		public static final String set_good = "set_good.php";
		public static final String setDiscuss = "set_discuss.php";
		public static final String getReview = "get_review.php";
		public static final String setReview = "set_review.php";
		public static final String getMyMessgae = "get_my_message.php";
		public static final String getOneMessage = "get_one_message.php";
		public static final String notice = "get_remind.php";
		public static final String delectRemind = "delect_remind.php";
		public static final String mySendMessage = "get_send_message.php";
		public static final String set_password = "set_user_password.php";
		public static final String addTranion = "addTranion.php";
		public static final String getTranion = "getTranion.php";
		public static final String addTranionDisscuss = "set_tranion_discuss.php";
		public static final String get_tranion_review = "get_tranion_review.php";
		public static final String getSendTranion = "get_send_Tranion.php";
		public static final String setTranionReview = "set_tranion_review.php";
		public static final String getTranionMyMessgae = "get_tranion_my_message.php";
		public static final String getTranionOneMessage="get_tranion_one_message.php";
		public static final String uploadscore="uploadScore.php";
		public static final String postLibraryKey ="post_library_key.php";

	}

	public static final class task {
		public static final int checkCode = 1001;
		public static final int ctguLogin = 1002;
		public static final int loginOut = 1003;
		public static final int getScore = 1004;
		public static final int getCourse = 1005;
		public static final int postCourse = 1006;

		public static final int getLostGoods = 3001;
		public static final int getFindGoods = 3002;
		public static final int getOneGoods = 3003;
		public static final int addGoods = 3004;

		public static final int getMessageBoard = 2001;
		public static final int userLogin = 2002;
		public static final int register = 2003;
		public static final int get_version = 2004;
		public static final int send_message = 2005;
		public static final int setMyUser = 2006;
		public static final int set_good = 2007;
		public static final int setDiscuss = 2008;
		public static final int getReview = 2009;
		public static final int setReview = 2010;
		public static final int getMyMessgae = 2011;
		public static final int getOneMessage = 2012;
		public static final int getMessageRemind = 2013;
		public static final int delectRemind = 2014;
		public static final int mySendMessage = 2015;
		public static final int set_password = 2016;
		public static final int getImage = 2017;
		public static final int getSendTranion = 2018;
		public static final int get_books = 3001;
		public static final int get_books_details = 3002;

		public static final int notice = 0001;

	}

	public static final class err {
		public static final String network = "网络错误";
		public static final String message = "消息错误";
		public static final String jsonFormat = "消息格式错误";
	}

	public static final class dir {
		@SuppressLint("SdCardPath")
		public static final String base = "/ctguhelp";
		public static final String faces = base + "/faces";
		public static final String images = base + "/images";
		public static final String qq = "http://www.ctguhelp.com/ctguhelp/images/";
	}
}