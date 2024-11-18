package hms.model.user;

import hms.service.user.DoctorServiceImpl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Staff
{
    public Doctor()
    {
    }

    public Doctor(String id, String name, String role, String gender, int age)
    {
        super(id, name, role, gender, age);
    }

    public Doctor(String id, String name, String role, String gender, int age, String password)
    {
        super(id, name, role, gender, age, password);
    }

    
}