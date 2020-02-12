package ru.brainix.ept.vkbox.activity.login;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ru.brainix.ept.vkbox.common.App;
import ru.brainix.ept.vkbox.room.Dao;
import ru.brainix.ept.vkbox.room.Database;
import ru.brainix.ept.vkbox.room.Entity;

public class LoginModel {

	private IPresesnterLogin mPresenter;

	LoginModel(IPresesnterLogin mPresenter){
		this.mPresenter = mPresenter;
	}

	public void loginComplete(){
		Completable.fromAction(new Action() {
			@Override
			public void run() throws Exception {
				dataChanger();
			}
		})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(() -> {
					mPresenter.databaseUpdated();
				});
	}

	private void dataChanger(){
		Database db = App.getInstance().getDatabase();
		Dao dao = db.dao();
		Entity entity = new Entity();
		entity.id = 1;
		entity.name = "Status";
		entity.status = true;
		dao.insert(entity);
	}

}
