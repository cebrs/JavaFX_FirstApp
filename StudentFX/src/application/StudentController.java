package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class StudentController implements Initializable {
	
	DBManager manager;
	Student selectedStudent;
	boolean editMode;
	
	@FXML 
	Label lblName;
	@FXML
	Label lblGender;
	@FXML
	Label lblListStudents;
	@FXML
	Label lblDOB;
	@FXML
	Label lblMark;
	@FXML
	Label lblComments;
	@FXML
	Label lblPhoto;
	@FXML
	Label lblStuDetails;
	
	@FXML
	TextField txtMark;
	@FXML
	TextArea txtComments;
	@FXML 
	TextField txtName;
	
	@FXML
	DatePicker dateDOB;
	@FXML
	ImageView imgPhoto;
	@FXML
	ListView<Student> listStudents;
	@FXML
	ComboBox<String> boxGender;
	@FXML
	Button btnEdit;
	@FXML
	Button btnSave;
	@FXML
	Button btnCancel;
	@FXML
	Label lblURL;
	@FXML
	Button btnNew;
	@FXML
	Button btnDelete;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		manager = new DBManager();
		selectedStudent = null;
		
		List<String> gvalues = new ArrayList<String>();
		gvalues.add("Male");
		gvalues.add("Female");
		ObservableList<String> gender = FXCollections.observableArrayList(gvalues);
		boxGender.setItems(gender);
		
		fetchStudents();
		
		listStudents.getSelectionModel().selectedItemProperty().addListener(e-> 
		displayStudentDetails(listStudents.getSelectionModel().getSelectedItem()));
	}
	
	public void fetchStudents()
	{
		List<Student> lsStudents = manager.loadStudents();
		if(lsStudents != null)
		{
			ObservableList<Student> students;
			students = FXCollections.observableArrayList(lsStudents);
			listStudents.setItems(students);
		}
	}
	
	private void displayStudentDetails(Student selectedStudent)
	{
		this.selectedStudent = selectedStudent;
		
		try
		{
			txtName.setText(selectedStudent.getName());
			boxGender.setValue(selectedStudent.getGender());
			txtMark.setText(String.valueOf(selectedStudent.getMark()));
			dateDOB.setValue(selectedStudent.getBirthDate());
			
			Image image;
			InputStream is = null;
			
			if(selectedStudent.getPhoto() != null)
			{
				is = new FileInputStream(selectedStudent.getPhoto());
				lblURL.setText(selectedStudent.getPhoto());
				image = new Image(is);
				imgPhoto.setImage(image);
			}
			else
			{
				is = new FileInputStream("C:/Users/celia/Documents/ESILV - A4/S1/Java&JEE/studentAno.jpg");
				image = new Image(is);
				imgPhoto.setImage(image);
				lblURL.setText("C:/Users/celia/Documents/ESILV - A4/S1/Java&JEE/studentAno.jpg");
			}
			
			txtMark.setText(String.valueOf(selectedStudent.getMark()));
			txtComments.setText(selectedStudent.getComments());
			
			btnEdit.disableProperty().set(false);
			btnDelete.disableProperty().set(false);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void onEdit()
	{
		btnCancel.disableProperty().set(false);
		btnSave.disableProperty().set(false);
		editMode = true;
	}
	
	public void onCancel()
	{
		btnCancel.disableProperty().set(true);
		btnSave.disableProperty().set(true);
	}
	
	public void chooseImage()
	{		
		FileChooser fileChooser = new FileChooser();
		String selectedFile = fileChooser.showOpenDialog(null).getAbsolutePath();
		selectedFile = selectedFile.replace("\\","/");
		
		Image image;
		InputStream is = null;
		try {
			is = new FileInputStream(selectedFile);
			image = new Image(is);
			imgPhoto.setImage(image);
			lblURL.setText(selectedFile);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onSave()
	{
		
		try
		{
			if(this.editMode == false)
			{
				selectedStudent = new Student(0, txtName.getText(), boxGender.getValue(), dateDOB.getValue(), lblURL.getText() ,Float.parseFloat(txtMark.getText()), txtComments.getText());
			}
			else
			{
				selectedStudent.setName(txtName.getText());
				selectedStudent.setGender(boxGender.getValue());
				selectedStudent.setBirthDate(dateDOB.getValue());
				selectedStudent.setMark(Float.parseFloat(txtMark.getText()));
				selectedStudent.setComments(txtComments.getText());
				selectedStudent.setPhoto(lblURL.getText());
			}
			
			if(this.editMode == false)
			{
				manager.addStudent(selectedStudent);
				
			}
			else
			{
				manager.updateStudent(selectedStudent);
				
			}
			onCancel();
			fetchStudents();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		editMode = false;
	}

	public void onNew()
	{
		btnCancel.disableProperty().set(false);
		btnSave.disableProperty().set(false);
		btnEdit.disableProperty().set(true);
		
		txtName.setText(null);
		txtMark.setText(null);
		txtComments.setText(null);
		lblURL.setText(null);
		dateDOB.setValue(null);
		boxGender.setValue(null);
		
		Image image;
		InputStream is = null;
		
		try {
			is = new FileInputStream("C:/Users/celia/Documents/ESILV - A4/S1/Java&JEE/studentAno.jpg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image = new Image(is);
		imgPhoto.setImage(image);
		lblURL.setText("C:/Users/celia/Documents/ESILV - A4/S1/Java&JEE/studentAno.jpg");
		
		editMode = false;
	}

	public void onDelete()
	{
		manager.deleteStudent(selectedStudent);
		btnDelete.disableProperty().set(true);
		fetchStudents();
	}
}