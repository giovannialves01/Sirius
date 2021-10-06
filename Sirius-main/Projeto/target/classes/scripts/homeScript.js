(function(){
            var files, 
                file, 
                extension,
                input = document.getElementById("fileURL"), 
                output = document.getElementById("fileOutput");
            
            input.addEventListener("change", function(e) {
                files = e.target.files;
                output.innerHTML = "";
                
                for (var i = 0, len = files.length; i < len; i++) {
                    file = files[i];
                    extension = file.name.split(".").pop();
                    output.innerHTML += "<li class='type-" + extension + "'>" + file.name + "</li>";
                }
            }, false);
})();
console.alert("doido");