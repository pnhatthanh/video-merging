const videoInput = document.getElementById('videoInput');
        const videoPreviewContainer = document.getElementById('videoPreview-container');
        videoInput.addEventListener('change', () => {
            videoInput.hidden=true;
            const files = Array.from(videoInput.files);
            files.forEach((file, index) => {
                const fileURL = URL.createObjectURL(file);
                const previewDiv = document.createElement('div');
                previewDiv.className = 'video-item';
                const videoElement = document.createElement('video');
                videoElement.src = fileURL;
                videoElement.controls = true;
                const removeButton = document.createElement('button');
                removeButton.innerText = 'X';
                removeButton.addEventListener('click', () => {
                    removeFile(file);
                    previewDiv.remove();
                });
                previewDiv.appendChild(videoElement);
                previewDiv.appendChild(removeButton);
                videoPreviewContainer.appendChild(previewDiv);
            });
        });
        function removeFile(_file) {
            const dataTransfer = new DataTransfer(); 
            const files = Array.from(videoInput.files);
            files.forEach((file, i) => {
                if (file != _file) dataTransfer.items.add(file);
            });
            videoInput.files = dataTransfer.files;
            if(videoInput.files.length === 0){
                videoPreviewContainer.innerHTML = '';
                videoInput.hidden=false;
            }
        }
        function removeAllFile(){
            videoInput.value = '';
            videoPreviewContainer.innerHTML = '';
            videoInput.hidden=false;
        }