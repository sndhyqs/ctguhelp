/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.ctgu.adapter;

import android.widget.BaseAdapter;
import android.content.Context;
import java.util.ArrayList;

import com.ctgu.ctguhelp.R;
import com.ctgu.model.Book;
import com.ctgu.model.Lost_Find_Message;
import com.ctgu.model.Message;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Library_Adapter extends BaseAdapter {
	private Context conext;
	private ArrayList<Book> books;

	public Library_Adapter(Context conext) {
		this.books = new ArrayList<Book>();
		this.conext = conext;
	}

	public void addItems(ArrayList<Book> newItems) {
		if ((newItems == null) || (newItems.size() <= 0)) {
			return;
		}
		for (int i = 0; i < newItems.size(); i++) {
			books.add(newItems.get(i));
		}

		notifyDataSetChanged();
	}

	public int getCount() {
		return books == null ? 0 : books.size();
	}

	public void clear() {
		books.clear();
		notifyDataSetChanged();
	}

	public Object getItem(int position) {
		return books.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Book message = (Book) books.get(position);
		View v = View.inflate(conext, R.layout.book_item, null);
		TextView bookTitle = (TextView) v.findViewById(R.id.bookTitle);
		TextView author = (TextView) v.findViewById(R.id.author);
		TextView publicationYear = (TextView) v.findViewById(R.id.publicationYear);
		TextView reserved = (TextView) v.findViewById(R.id.reserved);
		TextView available = (TextView) v.findViewById(R.id.available);
		TextView callNumber = (TextView) v.findViewById(R.id.callNumber);
		TextView publisher = (TextView) v.findViewById(R.id.publisher);

		bookTitle.setText(message.getBookTitle());
		author.setText(message.getAuthor());
		publicationYear.setText(message.getPublicationYear());
		reserved.setText(message.getReserved());
		available.setText(message.getAvailable());
		callNumber.setText(message.getCallNumber());
		publisher.setText(message.getPublisher());
		return v;
	}
}
