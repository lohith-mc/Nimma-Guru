# Nimma-Guru (ನಿಮ್ಮ-ಗುರು) 🕉️

**"Sharing Wisdom, Building Futures"**

Nimma-Guru is a community-driven mentorship platform designed to connect retired professionals (Gurus) with local students. It fosters a culture of "Gyaan-Daan" by providing a structured way for experts to volunteer their time and wisdom at local community centers (Samudaya Bhavanas).

---

## 🌟 Vision & Impact
Retired teachers, engineers, and artisans often have a desire to "Give Back" but lack a connection to students in need. Nimma-Guru bridges this gap, promoting **Active Aging** for retirees and providing **Quality Education** for students in rural and semi-urban areas.

---

## 🚀 Key Features

### 👨‍🏫 For Gurus
- **Simple Profile Creation:** Easy-to-use interface for elderly users to list skills (Math, Science, Carpentry, etc.) and availability.
- **Volunteer Tracking:** Manage free hours for weekend classes.
- **Wall of Fame:** Get recognized by the community through student appreciation notes.

### 🎓 For Students
- **Hyper-Local Discovery:** Find mentors in your own village or street.
- **Skill-Based Search:** Use interactive "Skill Chips" to filter mentors by subject.
- **Class Calendar:** View upcoming sessions at the local *Samudaya Bhavana*.
- **Express Gratitude:** Post "Thank You" notes to mentors, contributing to their rank on the Wall of Fame.

### 🌍 Inclusive Design
- **Bilingual Support:** Full interface support for **English** and **Kannada**.
- **Traditional Aesthetic:** A peaceful, spiritual Saffron and Gold theme with custom Mandala art patterns.

---

## 🛠️ Technical Stack
- **UI:** Jetpack Compose (Material 3)
- **Backend:** Firebase Firestore (Real-time NoSQL)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Navigation:** Type-safe Compose Navigation
- **Drawing:** Custom Canvas for decorative UI patterns

---

## 📊 System Workflow
The app follows a real-time reactive architecture:
1. **Guru** saves profile ➡️ **Firestore** updates ➡️ **Students** see the listing instantly via `SnapshotListeners`.
2. **Student** posts appreciation ➡️ **Atomic Transaction** updates Guru's count ➡️ **Wall of Fame** re-sorts automatically.
3. **Locale Change** ➡️ App dynamically pulls from `strings.xml` or `values-kn/strings.xml`.

---

## 📸 Screenshots
*(Add your screenshots here)*

---

## ⚙️ How to Run
1. Clone the repository.
2. Add your `google-services.json` to the `app/` directory.
3. Enable **Firestore Database** in your Firebase console.
4. Build and run using **Android Studio**.

---

## 📜 License
This project is developed as part of the **MindMatrix VTU Internship Program**.
