package com.zajel.zajelandroid.BookList.BooksModels;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;

public class BooksRepository {
    Realm realm;
    Context mContext;

    public BooksRepository(Context mContext) {
        this.mContext = mContext;
        realm = Realm.getDefaultInstance();
    }

    public BooksRepository(Realm realm) {
        this.realm = realm;
    }

    public void addConversation(final Book book) {

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    final RealmBook mRealmBook = new RealmBook();
                    Number currentIdNum = realm.where(RealmBook.class).max("_id");
                    int nextId = 0;

                    RealmBook realmBook = realm.where(RealmBook.class).equalTo("id", book.getId()).findFirst();

                    if (realmBook == null) {
                        if (currentIdNum == null) {
                            nextId = 0;
                        } else {
                            nextId = currentIdNum.intValue() + 1;
                        }
                    } else {
                        nextId = realmBook.getId();
                    }

                    mRealmBook.set_id(nextId);
                    mRealmBook.setId(book.getId());
                    mRealmBook.setTitle(book.getTitle());
                    mRealmBook.setAuthor(book.getAuthor());
                    mRealmBook.setPublishingYear(book.getPublishingYear());
                    mRealmBook.setLanguage(book.getLanguage());
                    mRealmBook.setPageNumber(book.getPageNumber());
                    mRealmBook.setImage(book.getImage());
                    mRealmBook.setApproved(book.getApproved());
                    mRealmBook.setGenre(book.getGenre());
                    mRealmBook.setStatus(book.getStatus());
                    realm.insertOrUpdate(mRealmBook);

                }
            });
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public RealmResults<RealmBook> getAllBooks() {
        return realm.where(RealmBook.class).findAll();
    }


    public void clearAllBooks() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(RealmBook.class);
            }
        });
    }

}
