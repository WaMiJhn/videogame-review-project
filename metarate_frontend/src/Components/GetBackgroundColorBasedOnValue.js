const getBackgroundColor = (rating) => {
    const colors = [
      '#f00',
      '#fc3',
      '#6c3',
      '#CCCCCC'
    ];
    if (rating < 5) {
      return colors[0];
    } else if (rating > 7) {
      return colors[2];
    } else if (rating === "tbd") {
      return colors[3];
    } else {
      return colors[1];
    }
  };
  
  export default getBackgroundColor;
  