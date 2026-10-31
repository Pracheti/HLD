import { useEffect, useState } from "react";
import "./App.css";

function App() {
  // Stores the counter value received from the backend.
  const [count, setCount] = useState(0);

  // Stores whether an API request is in progress.
  const [loading, setLoading] = useState(false);

  // Backend API URL.
  const API_URL = "http://localhost:8080/api/counter";

  // Fetch the current counter value from Spring Boot.
  const fetchCounter = async () => {
    try {
      setLoading(true);

      // Send a GET request to the backend.
      const response = await fetch(API_URL);

      // Convert the JSON response into a JavaScript object.
      const data = await response.json();

      // Update the counter displayed in React.
      setCount(data.value);
    } catch (error) {
      console.error("Error fetching counter:", error);
      alert("Could not connect to the backend.");
    } finally {
      setLoading(false);
    }
  };

  // Increment the counter through the backend.
  const incrementCounter = async () => {
    try {
      setLoading(true);

      // Send a POST request to increment the counter.
      const response = await fetch(`${API_URL}/increment`, {
        method: "POST",
      });

      // Read the updated counter value.
      const data = await response.json();

      // Update the UI with the backend's response.
      setCount(data.value);
    } catch (error) {
      console.error("Error incrementing counter:", error);
      alert("Could not increment the counter.");
    } finally {
      setLoading(false);
    }
  };

  // Fetch the counter once when the component first loads.
  useEffect(() => {
    fetchCounter();
  }, []);

  return (
    <div className="app">
      <h1>Live Counter</h1>

      <h2>{count}</h2>

      <button onClick={incrementCounter} disabled={loading}>
        Increase
      </button>

      <button onClick={fetchCounter} disabled={loading}>
        Refresh
      </button>
    </div>
  );
}

export default App;