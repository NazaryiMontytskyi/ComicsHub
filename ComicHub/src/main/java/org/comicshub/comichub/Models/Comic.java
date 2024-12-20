package org.comicshub.comichub.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.comicshub.comichub.Security.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comics")
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="genre_id", referencedColumnName = "id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @Column(name="author")
    private String author;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pdf_file_id", referencedColumnName = "id")
    private PDFFile pdfFile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_file_id", referencedColumnName = "id")
    private ImageContent titleImage;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "comic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ComicComment> comments;


}
