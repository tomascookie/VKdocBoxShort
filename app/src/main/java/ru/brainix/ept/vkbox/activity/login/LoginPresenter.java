package ru.brainix.ept.vkbox.activity.login;

public class LoginPresenter implements IPresesnterLogin{

	private IViewLogin mainView;
	private LoginModel mainModel;

	LoginPresenter(IViewLogin mainView){
		this.mainView = mainView;
		mainModel = new LoginModel(this);
	}

	@Override
	public void databaseUpdated(){
		mainView.loginComplete();

	}


	public void activityStarted(){
		mainView.setTitleFont();
	}

	public void loginStarted(){
		mainView.tryLogin();
	}

	public void loginComplete() {
		mainModel.loginComplete();
	};

	public void loginError(){
		//Implement Error
		mainView.loginError();
	}

	public void activityDestroy(){
		mainView = null;
		mainModel = null;
	}



}
