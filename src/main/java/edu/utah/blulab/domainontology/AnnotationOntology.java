package edu.utah.blulab.domainontology;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AnnotationOntology {
    private DomainOntology domain;
    private static OWLOntologyManager manager;
    private static OWLOntology annotationOntology;
    private static OWLDataFactory factory;
    private static String annotationURI;


    public AnnotationOntology(DomainOntology domain) throws Exception{
       // File parentDirectory = domain.getOntFile().getParentFile();
        manager = OWLManager.createOWLOntologyManager();
        factory = manager.getOWLDataFactory();
        this.domain = domain;


        String fileName = domain.getOntFile().getPath().substring(0, domain.getOntFile().getPath().indexOf(".owl")) +
                "_Annotations.owl";
        String documentIRI = fileName;
        File annotationFile = new File(documentIRI);
        if(!annotationFile.exists()){
            annotationFile.createNewFile();String temp = domain.getDomainURI().substring(0, domain.getDomainURI().lastIndexOf(".owl"));
            System.out.println("Creating file " + annotationFile.toString());
            annotationURI = temp + "_Annotations.owl";

            SimpleIRIMapper mapper = new SimpleIRIMapper(IRI.create(annotationURI), IRI.create(annotationFile));
            manager.addIRIMapper(mapper);
            manager.createOntology(IRI.create(annotationURI));
            annotationOntology = manager.getOntology(IRI.create(annotationURI));
            SimpleIRIMapper importMapper = new SimpleIRIMapper(IRI.create(domain.getDomainURI()),
                    IRI.create(domain.getOntFile()));
            manager.addIRIMapper(importMapper);
            OWLImportsDeclaration importsDeclaration = factory.getOWLImportsDeclaration(IRI.create(domain.getDomainURI()));
            manager.applyChange(new AddImport(annotationOntology, importsDeclaration));

            manager.loadOntology(IRI.create(domain.getDomainURI()));
            manager.setOntologyFormat(annotationOntology, new OWLXMLOntologyFormat());
            System.out.println("Created " + annotationOntology.getOntologyID().toString());
            for(OWLOntology importOnt : annotationOntology.getDirectImports()){
                System.out.println("Imported " + importOnt.getOntologyID().toString());
            }

            manager.saveOntology(annotationOntology);
        }else{
            new AnnotationOntology(annotationFile.getPath(), domain);
        }


        //manager.saveOntology(annotationOntology);
   }

   public AnnotationOntology(String annotationFileName, DomainOntology domain) throws Exception{
       this.domain = domain;

       manager = OWLManager.createOWLOntologyManager();
       File annotationFile = new File(annotationFileName);
       annotationOntology = manager.loadOntologyFromOntologyDocument(annotationFile);
       factory = manager.getOWLDataFactory();
       annotationURI = annotationOntology.getOntologyID().getOntologyIRI().toString();


   }

   public DomainOntology getDomainOntology(){
       return domain;
   }

   public void createAnnotationInstance(String annotationID, String clsURI){
       OWLNamedIndividual annotationInstance = factory.getOWLNamedIndividual(IRI.create(annotationURI+"#"+annotationID));
       OWLClass annotationClass = factory.getOWLClass(IRI.create(clsURI));
       OWLAxiom annotationAxiom = factory.getOWLClassAssertionAxiom(annotationClass, annotationInstance);
       manager.addAxiom(annotationOntology, annotationAxiom);
   }

   public void setAnnotationSpan(String annotationID){
       OWLNamedIndividual annotationInstance = factory.getOWLNamedIndividual(IRI.create(annotationURI+"#"+annotationID));
   }
}
