package ru.brainix.ept.vkbox.activity.main;

import java.util.List;
import ru.brainix.ept.vkbox.docs.data.DocumentModel;

public interface IViewMain {

	void goToLoginUnkownBitch();

	void setUi();

	void uploadDocWindow();

	void textVisible();

	void pbVisible();

	void textInvisible();

	void pbInvivible();

	void fileIncorrect();

	void setRecyclerView(String typeDoc, List<DocumentModel> list);

	void bottomMenu();

	void settingMenu();

	void getDialogFragment(String owner_id, String doc_id);
}
