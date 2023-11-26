import '../css/ConfirmationForm.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import Popup from 'reactjs-popup';

const ConfirmationForm = (props) => {
    return (
        <section id="confirmationform">
            <FontAwesomeIcon className='close-button' onClick={props.onClose} icon={faXmark} />
            <div className="confirmationform-container">
                <h2>{props.title}</h2>
                <div className='info'>
                    <div>
                        {props.content}
                    </div>
                    <div className='confirmation-form-buttons'>
                        <button className='confirmbtn-yes' onClick={props.onConfirm}>Yes</button>
                        <button className='confirmbtn-no' onClick={props.onClose}>No</button>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default ConfirmationForm;