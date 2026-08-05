async function doShowRandomDog() {
    try {
        const response = await fetch('https://dog.ceo/api/breeds/image/random');
        const data = await response.json();
        const imgElement = document.getElementById('dogImage');
        imgElement.src = data.message;
        imgElement.style.display = 'block';
    } catch (error) {
        console.error('Error fetching random dog image:', error);
    }
}
