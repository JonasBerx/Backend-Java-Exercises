# RESTful JAX-RS Application : Gestion sécurisée (JWT) de données de films

## RESTful API : opérations disponibles

### Opérations associées à la gestion des utilisateurs et l'authentification

<br>
<table style="caption-side: top">
<caption>Opérations sur les ressources de type "Authentification"</caption>
<tr>
    <th>URI</th>
    <th>Méthode</th>
    <th>Auths?</th>
    <th>Opération</th>
</tr>

<tr>
    <td>auths/login</td>
    <td>POST</td>
    <td>Non</td>
    <td>
    Vérifier les « credentials » d’un User et renvoyer le User et un token JWT s’ils sont OK
    </td>
</tr>
<tr>
    <td>auths/register</td>
    <td>POST</td>
    <td>Non</td>
    <td>
    Créer une ressource User et un token JWT et les renvoyer
    </td>
</tr>

</table>

<br>

<table style="caption-side: top">
<caption>Opérations sur les ressources de type "User"</caption>
<tr>
    <th>URI</th>
    <th>Méthode</th>
    <th>Auths?</th>
    <th>Opération</th>
</tr>

<tr>
    <td>users/init</td>
    <td>POST</td>
    <td>Non</td>
    <td>
    CREATE ONE : Créer une ressource basée sur des données par défaut (login = "james",password= "password")
    </td>
</tr>
<tr>
    <td>users/me</td>
    <td>GET</td>
    <td>JWT</td>
    <td>
    READ ONE : Lire la ressource identifiée par le biais du token donné dans le header de la requête
    </td>
</tr>

</table>

### Opérations associées à la gestion des films

<table style="caption-side: top">
<caption>Opérations sur les ressources de type "Blog"</caption>
<tr>
    <th>URI</th>
    <th>Méthode</th>
    <th>Auths?</th>
    <th>Opération</th>
</tr>

<tr>
    <td>blog</td>
    <td>GET</td>
    <td>JWT</td>
    <td>
    READ ALL : Lire toutes les resources de la collection
    </td>
</tr>
<tr>
    <td>blog/{id}</td>
    <td>GET</td>
    <td>JWT</td>
    <td>
    READ ONE : Lire la ressource identifiée
    </td>
</tr>

<tr>
    <td>blogs</td>
    <td>POST</td>
    <td>JWT</td>
    <td>
    CREATE ONE : Créer une resource basée sur les données de la requête
    </td>
</tr>


</table>
