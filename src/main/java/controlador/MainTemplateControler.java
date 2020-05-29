/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author jadaxi
 *
 * clase encargada de mantener la seguridad de la pagina, compurba si el usurio
 * que esta en el sistema esta permitido, de lo contrario redireciona a otra
 * pagina
 */
@Named
@ViewScoped
public class MainTemplateControler implements Serializable {

    private User us;
    private int progreso;

    @PostConstruct
    public void inicio() {
        us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        int level = us.getLv();
        int exp = (int) us.getXp();
        progreso = exp % 100;
    }

    public void checkAndShow() throws IOException {

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") == null) {

            System.out.println("La plantilla dice que el ususrio no esta autorizado");
            FacesContext.getCurrentInstance().getExternalContext().redirect("notAcess.xhtml");
        }

    }

    public User getUs() {
        return us;
    }

    public void setUs(User us) {
        this.us = us;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }
    
    

    public String out() {
        System.out.println("logOut");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return "../index.xhtml";

    }

    public String goToCreateRecipe() {

        System.out.println("ENTRA IU");

        if (((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv() < 2) {
            System.out.println("NIVEL MENOR");

            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage("ERROR", "No tienes nivel para crear recetas, necesitas nivel 2. Tu nivel actual: " + ((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv()));

            return "";

        } else {
            return "createRecipe.xhtml?faces-redirect=true";
        }

    }

    public String goToAdmin() {

        System.out.println("ENTRA Isdcfvbnm,");

        if (((User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getLv() < 100) {
            System.out.println("NIVEL MENOR");

            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage("ERROR", "No puedes acceder si no eres administrador"));

            return "";

        } else {
            return "admin.xhtml?faces-redirect=true";
        }

    }

}
