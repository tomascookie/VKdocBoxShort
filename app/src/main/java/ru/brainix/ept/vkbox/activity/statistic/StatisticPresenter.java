package ru.brainix.ept.vkbox.activity.statistic;


public class StatisticPresenter {

	private IViewStatistic mainView;
	private StatisticModel mainModel;

	StatisticPresenter(IViewStatistic mainView){

		mainModel = new StatisticModel();
		this.mainView = mainView;
	}


	public void activityStarted(){
		mainView.setTitleFont();
		mainView.setStatisticText(getStringCount());
	}

	public void activityDestroyed(){

		mainModel = null;
		mainView = null;
	}

	//Обновляем счетчик статистики файлов
	public String getStringCount(){

		return mainModel.getCount();
	}



}
