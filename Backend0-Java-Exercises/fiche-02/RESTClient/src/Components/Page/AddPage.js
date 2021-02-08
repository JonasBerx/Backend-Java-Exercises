import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import callAPI from "../../utils/api.js";
import PrintError from "../PrintError.js";

const API_BASE_URL = "/api/pages/";

let addPagePage = `<h4 id="pageTitle">Add a page</h4>
<form>
<div class="form-group">
  <label for="title">Enter title</label>
  <input
    class="form-control"
    type="text"
    name="title"
    id="title"
    required
  />
</div>
<div class="form-group">
  <label for="content">Enter content</label>
  <input
    class="form-control"
    type="text"
    name="content"
    id="content"
    required
  />
</div>
<div class="form-group">
  <label for="pubStatus">Enter publishing status</label>
  <input
    class="form-control"
    type="text"
    name="pubStatus"
    id="pubStatus"
    required
  />
</div>
<div class="form-group">
  <label for="uri">Enter Url</label>
  <input
    class="form-control"
    type="url"
    name="uri"
    id="uri"
    required
  />
</div>
<input type="submit" class="btn btn-primary" value="Add Film" />
</form>`;

const AddPagePage = () => {  

  let page = document.querySelector("#page");
  page.innerHTML = addPagePage;
  let filmForm = document.querySelector("form");
  filmForm.addEventListener("submit", onAddFilm);
};

const onAddFilm = async (e) => {
  e.preventDefault();
  let auteurId = JSON.parse(localStorage.getItem("user"));
  console.log(auteurId)
  let page = {
    title: document.getElementById("title").value,
    content: document.getElementById("content").value,
    pubStatus: document.getElementById("pubStatus").value,
    uri: document.getElementById("uri").value,
    auteur: auteurId.user.id
  };

  const user = getUserSessionData();

  try {
    const pageAdded = await callAPI(
      API_BASE_URL ,
      "POST",
      user.token,
      page
    );
    onFilmAdded(pageAdded);
  } catch (err) {
    console.error("AddPagePage::onAddPage", err);
    PrintError(err);
  }  
}

const onFilmAdded = (data) => {
  console.log(data);  
  RedirectUrl("/pages");
};

export default AddPagePage;
