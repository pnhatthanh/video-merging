const videoInput = document.getElementById('videoInput');
const videoPreviewContainer = document.getElementById('videoPreview-container');
videoInput.addEventListener('change', () => {
	videoInput.hidden = true;
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
	if (videoInput.files.length === 0) {
		videoPreviewContainer.innerHTML = '';
		videoPreviewContainer.appendChild(videoInput);
		videoInput.hidden = false;
	}
}
function removeAllFile() {
	videoInput.value = '';
	videoPreviewContainer.innerHTML = '';
	videoPreviewContainer.appendChild(videoInput);
	videoInput.hidden = false;
}
const uploadForm = document.getElementById('uploadForm');
const progressBar = document.getElementById('progressBar');
const progressText = document.getElementById('progressText');
const progressInfo = document.getElementById('progressInfo');
const processID = document.getElementById('processID');
const buttonReset=document.getElementById('button_reset')
uploadForm.addEventListener('submit', function(e) {
	e.preventDefault();

	progressInfo.style.display = 'block';

	const formData = new FormData(uploadForm);
	const xhr = new XMLHttpRequest();

	xhr.open('POST', uploadForm.action, true);
	xhr.send(formData);
	buttonReset.disabled=true;
	// Periodically query the server for upload progress
	const interval = setInterval(() => {
		fetch('http://localhost:8080/Video_Merging/UploadServlet')
			.then(response => response.json())
			.then(data => {
				let uploaded = data.numFileUploaded;
				let total = data.totalFileUploaded;
				if (total > 0) {
					const progress = (uploaded / total) * 100;
					progressBar.value = progress;
					progressText.textContent = 'Uploaded: ' + uploaded + ' of ' + total;
				}
				else {
					progressText.textContent = 'Progressing';

				}
				if (uploaded === total && uploaded) {
					clearInterval(interval);
					progressText.textContent = "Upload sucessfully"
					fetch('http://localhost:8080/Video_Merging/UploadServlet/Finish');
					fetch('http://localhost:8080/Video_Merging/MergeVideoServlet', {
						method: "post"
					});
					window.location.replace('http://localhost:8080/Video_Merging/home');
				}
			});
	}, 1000);


});
const listVideoUncomplete = document.querySelectorAll(".video.uncomplete");
const listVideoUncompleteIntervalID = []
for (let i = 0; i < listVideoUncomplete.length; i++) {
	let videoID = listVideoUncomplete[i].id;
	let video = listVideoUncomplete[i];
	listVideoUncompleteIntervalID.push(setInterval(() => {
		let progressBarMerge = listVideoUncomplete[i].querySelector("#progressBarMerge");
		let progressBarHD = listVideoUncomplete[i].querySelector("#progressBarHD");
		let processInfo = listVideoUncomplete[i].querySelector("#processInfo");
		let downloadButton = listVideoUncomplete[i].querySelector(".button-download");
		fetch('http://localhost:8080/Video_Merging/MergeVideoServlet?pID=' + videoID)
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				let progressMerge = data.progressMerge;
				let progressHD = data.progressHD;
				progressBarMerge.value = progressMerge;
				progressBarHD.value = progressHD;
				if (Math.abs(progressMerge - 100) < 1e-6 && Math.abs(progressHD - 100) < 1e-6) {
					clearInterval(listVideoUncompleteIntervalID[i]);
					processInfo.style = "display:none";
					downloadButton.style = "display:block";
					video.classList.remove("uncomplete");
					video.classList.add("complete");
				}
			})
	}, 1000));
} 