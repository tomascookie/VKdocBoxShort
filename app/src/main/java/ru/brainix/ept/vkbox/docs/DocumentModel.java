package ru.brainix.ept.vkbox.docs;

public class DocumentModel {

    private String titleDoc;
    private String sizeDoc;
    private String dateDoc;
    private String imageFileType;
    private String url;
    private String idDoc;
    private int type;
    private int ownerId;

    //Конструктор в качестве сеттера
    public DocumentModel(String titleDoc, String sizeDoc, String dateDoc, String imageFileType, String url, String idDoc, int type, int ownerId){

        this.titleDoc=titleDoc;
        this.sizeDoc=sizeDoc;
        this.dateDoc=dateDoc;
        this.imageFileType=imageFileType;
        this.url = url;
        this.idDoc = idDoc;
        this.type = type;
        this.ownerId = ownerId;

    }


    //Геттеры для получения данных моделей
    public int getType() {
        return this.type;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getTitleDoc() {
        return this.titleDoc;
    }

    public String getSizeDoc() {
        return this.sizeDoc;
    }

    public String getDateDoc() {
        return this.dateDoc;
    }

    public String getImageFileType() {
        return this.imageFileType;
    }

    public String getIdDoc() {
        return this.idDoc;
    }

    public String getUrl() {
        return this.url;
    }


    //Сеттеры не нужны, но пусть пока будут
    public void setDateDoc(String dateDoc) {
        this.dateDoc = dateDoc;
    }

    public void setSizeDoc(String sizeDoc) {
        this.sizeDoc = sizeDoc;
    }

    public void setTitleDoc(String titleDoc) {
        this.titleDoc = titleDoc;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

}
