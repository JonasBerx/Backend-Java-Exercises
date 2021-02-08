import { RedirectUrl } from "../Router.js";
import { getUserSessionData } from "../../utils/session.js";
import callAPI from "../../utils/api.js";
import PrintError from "../PrintError.js";
const API_BASE_URL = "/api/pages/";

const PageListPage = async () => {
  // deal with page title
  let page = document.querySelector("#page");
  // clear the page
  page.innerHTML = "";
  let title = document.createElement("h4");
  title.id = "pageTitle";
  title.innerText = "List of pages";
  page.appendChild(title);

  const user = getUserSessionData();

  try {
    const pages = await callAPI(API_BASE_URL, "GET", user.token);
    onPageList(pages);
  } catch (err) {
    console.error("PageListPage::onPageList", err);
    PrintError(err);
  }
};

const onPageList = (data) => {
  if (!data) return;
  let table = `
  <div id="tablePages" class="table-responsive mt-3">
  <table class="table">
      <thead>
          <tr>
              <th class="title">Title</th>
              <th class="uri">URL</th>
              <th class="content">Content</th>
              <th class="status">Pub Status</th>
              <th class="auteur">Author</th>
              <th class="save">Save</th>
              <th class="delete">Delete</th>
          </tr>
      </thead>
      <tbody>`;

  data.forEach((element) => {
    table += `<tr data-id="${element.id}">
                <td class="title" contenteditable="true">${element.title}</td>
                <td class="link" contenteditable="true"><a href="${element.uri}" target="_blank">${element.uri}</a></td>
                <td class="content" contenteditable="true">${element.content}</td>
                <td class="status" contenteditable="true">${element.pubStatus}</td>
                <td class="auteur" contenteditable="false">${element.auteur}</td>
                <td class="save"><button class="btn btn-primary saveBtn">Save</button></td>
                <td class="delete"><button class="btn btn-dark deleteBtn">Delete</button></td>
            </tr>
            `;
  });

  table += `</tbody>
  </table>
  </div>`;
  page.innerHTML += table;

  page.innerHTML +=
    '<button id="addBtn" class="btn btn-primary mt-2">Add Page</button>';

  const saveBtns = document.querySelectorAll(".saveBtn");
  const deleteBtns = document.querySelectorAll(".deleteBtn");
  deleteBtns.forEach((deleteBtn) => {
    deleteBtn.addEventListener("click", onDelete);
  });

  saveBtns.forEach((saveBtn) => {
    saveBtn.addEventListener("click", onSave);
  });

  const addBtn = document.querySelector("#addBtn");
  addBtn.addEventListener("click", onAddPage);
};

const onSave = async (e) => {
  // the id is given in the current table row under data-id attribute
  const pageId = e.target.parentElement.parentElement.dataset.id;
  let page = {};
  const tr = e.target.parentElement.parentElement;
  const cells = tr.querySelectorAll("td");
  page.title = cells[0].innerText;
  page.uri = cells[1].innerText;
  page.content = cells[2].innerText;
  page.pubStatus = cells[3].innerText;
  page.auteur = cells[4].innerText;
  console.log("Page:", page);
  const user = getUserSessionData();

  try {
    const pageUpdated = await callAPI(
      API_BASE_URL + pageId,
      "PUT",
      user.token,
      page
    );
    await PageListPage();
  } catch (err) {
    console.error("PageListPage::onSave", err);
    PrintError(err);
  }
};

const onDelete = async (e) => {
  // the id is given in the current table row under data-id attribute
  const pageId = e.target.parentElement.parentElement.dataset.id;
  const user = getUserSessionData();

  try {
    const pageDeleted = await callAPI(
      API_BASE_URL + pageId,
      "DELETE",
      user.token
    );
    PageListPage();
  } catch (err) {
    console.error("PageListPage::onDelete", err);
    PrintError(err);
  }
};

const onAddPage = () => {
  RedirectUrl("/pages/add");
};

export default PageListPage;
