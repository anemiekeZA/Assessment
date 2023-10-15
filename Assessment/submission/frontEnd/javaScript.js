//How would you dynamically target firstDiv and make it's colour pink? (provide the code snippet)

const box = document.getElementById("firstDiv");

window.addEventListener("load", () => {
  box.style.backgroundColor = "#FFC0CB"; //
});

//How would you dynamically target secondDiv and add the yellow-card class to its class list? (provide the code snippet)
var div2 = document.getElementById("secondDiv");
div2.className += " yellow-card";

const box1 = document.getElementById("secondDiv");

window.addEventListener("load", () => {
  box1.style.backgroundColor = "bisque";
});

