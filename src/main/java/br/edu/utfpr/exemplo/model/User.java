package br.edu.utfpr.exemplo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "DT_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String nome;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String telefone;

    @Column(name = "CPF")
    private String cpf;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Address> addresses;

    /*
    USER
    ID / NOME / EMAIL / CPF
    1   CLEVERSON ABB ABC

    ADDRESS
    ID / STREET / type          / user (user_id)
    1    rua x    apartamento   1
    2    rua y    trabalho      1
     */
    @ManyToMany
    @JoinTable(
            name = "USER_PROFILE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROFILE_ID"))
    private List<Profile> profiles;

    /*
    USER
    ID / NOME / EMAIL / CPF
    1   CLEVERSON ABB ABC
    2   JOÃO ABB ABC

    USER_PROFILE
    ID USER_ID PROFILE_ID
    1   1   2
    2   1   3
    3   2   1

    PROFILE
    ID / NAME    / USER (user_id)
    1    ADMIN
    2    USER
    3    STOCK_USER
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
