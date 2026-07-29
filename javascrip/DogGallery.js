async function doShowRandomDog(): Promise<void> {
    try {
        let response: Response = await fetch('https://dog.ceo/api/breeds/image/random');
        let data: { message: string } = await response.json();
        let imgElement: HTMLImageElement = document.getElementById('dogImage') as HTMLImageElement;
        imgElement.src = data.message;
        imgElement.style.display = 'block';
    } catch (error) {
        console.error('Error fetching random dog image:', error);
    }
}
