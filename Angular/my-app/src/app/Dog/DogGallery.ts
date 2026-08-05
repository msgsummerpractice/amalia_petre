async function doShowRandomDog() {
    try {
        const response: Response = await fetch('https://dog.ceo/api/breeds/image/random');
        const data: { message: string } = await response.json();
        const imgElement: HTMLImageElement = document.getElementById('dogImage') as HTMLImageElement;
        imgElement.src = data.message;
        imgElement.style.display = 'block';
    } catch (error) {
        console.error('Error fetching random dog image:', error);
    }
}
